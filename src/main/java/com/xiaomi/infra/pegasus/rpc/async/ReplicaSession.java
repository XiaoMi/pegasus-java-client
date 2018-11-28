// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.rpc.async;

import com.xiaomi.infra.pegasus.apps.negotiation_message;
import com.xiaomi.infra.pegasus.apps.negotiation_status;
import com.xiaomi.infra.pegasus.base.blob;
import com.xiaomi.infra.pegasus.base.error_code.error_types;

import com.xiaomi.infra.pegasus.base.rpc_address;
import com.xiaomi.infra.pegasus.operator.client_operator;
import com.xiaomi.infra.pegasus.operator.negotiate_operator;
import com.xiaomi.infra.pegasus.rpc.ReplicationException;
import com.xiaomi.infra.pegasus.thrift.protocol.TMessage;
import io.netty.channel.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.socket.SocketChannel;

import org.slf4j.Logger;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.sasl.Sasl;
import javax.security.sasl.SaslClient;
import java.net.UnknownHostException;
import java.security.PrivilegedExceptionAction;
import java.util.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by weijiesun on 17-9-13.
 */
public class ReplicaSession {
    public static class RequestEntry {
        public int sequenceId;
        public com.xiaomi.infra.pegasus.operator.client_operator op;
        public Runnable callback;
        public ScheduledFuture timeoutTask;
        public long timeoutMs;
    }

    public enum ConnState {
        CONNECTED,
        CONNECTING,
        NEGOTIATION,
        DISCONNECTED
    }

    public ReplicaSession(rpc_address address, EventLoopGroup rpcGroup, int socketTimeout) {
        this(address, rpcGroup, socketTimeout, false, null, null, null);
    }

    // You can specify a message response filter with constructor or with "setMessageResponseFilter" function.
    // the mainly usage of filter is test, in which you can control whether to abaondon a response
    // and how to abandon it, so as to emulate some network failure cases
    public ReplicaSession(rpc_address address, EventLoopGroup rpcGroup, int socketTimeout, MessageResponseFilter filter) {
        this(address, rpcGroup, socketTimeout, false, null, null, null);
        this.filter = filter;
    }

    public ReplicaSession(rpc_address address, EventLoopGroup rpcGroup, int socketTimeout, boolean openAuth, Subject subject, String serviceName, String serviceFqdn) {
        this.address = address;
        this.rpcGroup = rpcGroup;

        final ReplicaSession this_ = this;
        boot = new Bootstrap();
        boot.group(rpcGroup).channel(ClusterManager.getSocketChannelClass())
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, socketTimeout)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("ThriftEncoder", new ThriftFrameEncoder());
                        pipeline.addLast("ThriftDecoder", new ThriftFrameDecoder(this_));
                        pipeline.addLast("ClientHandler", new ReplicaSession.DefaultHandler());
                    }
                });

        this.openAuth = openAuth;
        if (openAuth) {
            this.subject = subject;
            this.serviceName = serviceName;
            this.serviceFqdn = serviceFqdn;
            // QOP(Quality of Protection) mismatch between client and server may cause the error - No common protection layer between client and server
            props.put(Sasl.QOP, "auth");
        } else {
            this.subject = new Subject();
        }
    }

    public ReplicaSession(rpc_address address, EventLoopGroup rpcGroup, int socketTimeout, boolean openAuth, Subject subject, String serviceName, String serviceFqdn, MessageResponseFilter filter) {
        this(address, rpcGroup, socketTimeout, true, subject, serviceName, serviceFqdn);
        this.filter = filter;
    }

    public void setMessageResponseFilter(MessageResponseFilter filter) {
        this.filter = filter;
    }

    public int asyncSend(client_operator op, Runnable callbackFunc, long timeoutInMilliseconds) {
        RequestEntry entry = new RequestEntry();
        entry.sequenceId = seqId.getAndIncrement();
        entry.op = op;
        entry.callback = callbackFunc;
        //NOTICE: must make sure the msg is put into the pendingResponse map BEFORE
        //the timer task is scheduled.
        pendingResponse.put(new Integer(entry.sequenceId), entry);
        entry.timeoutTask = addTimer(entry.sequenceId, timeoutInMilliseconds);
        entry.timeoutMs = timeoutInMilliseconds;

        // We store the connection_state & netty channel in a struct so that they can fetch and update in atomic.
        // Moreover, we can avoid the lock protection when we want to get the netty channel for send message
        VolatileFields cache = fields;
        if (cache.state == ConnState.CONNECTED) {
            write(entry, cache);
        } else {
            boolean needConnect = false;
            synchronized (pendingSend) {
                cache = fields;
                if (cache.state == ConnState.CONNECTED) {
                    write(entry, cache);
                } else {
                    pendingSend.offer(entry);
                    if (cache.state == ConnState.DISCONNECTED) {
                        cache = new VolatileFields();
                        cache.state = ConnState.CONNECTING;
                        fields = cache;
                        needConnect = true;
                    }
                }
            }
            if (needConnect) {
                doConnect();
            }
        }
        return entry.sequenceId;
    }

    public void closeSession() {
        VolatileFields f = fields;
        if (f.state == ConnState.CONNECTED && f.nettyChannel != null) {
            try {
                f.nettyChannel.close().sync();
                logger.info("channel to {} closed", address.toString());
            } catch (Exception ex) {
                logger.warn("close channel {} failed: ", address.toString(), ex);
            }
        } else {
            logger.info("channel {} not connected, skip the close", address.toString());
        }
    }

    public RequestEntry getAndRemoveEntry(int seqID) {
        return pendingResponse.remove(new Integer(seqID));
    }

    public final String name() {
        return address.toString();
    }

    public final rpc_address getAddress() {
        return address;
    }

    private void doConnect() {
        try {
            // we will receive the channel connect event in DefaultHandler.ChannelActive
            boot.connect(address.get_ip(), address.get_port()).addListener(
                    new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            if (channelFuture.isSuccess()) {
                                logger.info("{}: start to async connect to target, wait channel to active", name());
                            } else {
                                logger.warn("{}: try to connect to target failed: ", name(), channelFuture.cause());
                                markSessionDisconnect();
                            }
                        }
                    }
            );
        } catch (UnknownHostException ex) {
            logger.error("invalid address: {}", address.toString());
            assert false;
        }
    }

    private void markSessionConnected(Channel activeChannel) {
        VolatileFields newCache = new VolatileFields();
        newCache.state = ConnState.CONNECTED;
        newCache.nettyChannel = activeChannel;

        synchronized (pendingSend) {
            while (!pendingSend.isEmpty()) {
                RequestEntry e = pendingSend.poll();
                if (pendingResponse.get(new Integer(e.sequenceId)) != null) {
                    write(e, newCache);
                } else {
                    logger.info("{}: {} is removed from pending, perhaps timeout", name(), e.sequenceId);
                }
            }
            fields = newCache;
        }
    }

    private void markSessionNegotiation(Channel activeChannel) {
        VolatileFields newCache = new VolatileFields();
        newCache.state = ConnState.NEGOTIATION;
        newCache.nettyChannel = activeChannel;
        fields = newCache;
        logger.info("{}: mark session state negotiation, now negotiate", name());
        startNegotiation();
    }

    private void markSessionDisconnect() {
        VolatileFields cache = fields;
        synchronized (pendingSend) {
            if (cache.state != ConnState.DISCONNECTED) {
                // NOTICE:
                // 1. when a connection is reset, the timeout response
                // is not answered in the order they query
                // 2. It's likely that when the session is disconnecting
                // but the caller of the api query/asyncQuery didn't notice
                // this. In this case, we are relying on the timeout task.
                while (!pendingSend.isEmpty()) {
                    RequestEntry e = pendingSend.poll();
                    tryNotifyWithSequenceID(e.sequenceId, error_types.ERR_SESSION_RESET, false);
                }
                List<RequestEntry> l = new LinkedList<RequestEntry>();
                for (Map.Entry<Integer, RequestEntry> entry : pendingResponse.entrySet()) {
                    l.add(entry.getValue());
                }
                for (RequestEntry e : l) {
                    tryNotifyWithSequenceID(e.sequenceId, error_types.ERR_SESSION_RESET, false);
                }

                cache = new VolatileFields();
                cache.state = ConnState.DISCONNECTED;
                cache.nettyChannel = null;
                fields = cache;
            } else {
                logger.warn("{}: session is closed already", name());
            }
        }
    }

    private void tryNotifyWithSequenceID(
            int seqID,
            error_types errno,
            boolean isTimeoutTask) {
        logger.debug("{}: {} is notified with error {}, isTimeoutTask {}",
                name(), seqID, errno.toString(), isTimeoutTask);
        RequestEntry entry = pendingResponse.remove(new Integer(seqID));
        if (entry != null) {
            if (!isTimeoutTask)
                entry.timeoutTask.cancel(true);
            entry.op.rpc_error.errno = errno;
            entry.callback.run();
        } else {
            logger.warn("{}: {} is removed by others, current error {}, isTimeoutTask {}",
                    name(), seqID, errno.toString(), isTimeoutTask);
        }
    }

    private void write(final RequestEntry entry, VolatileFields cache) {
        cache.nettyChannel.writeAndFlush(entry).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                //NOTICE: we never do the connection things, this should be the duty of
                //ChannelHandler, we only notify the request
                if (!channelFuture.isSuccess()) {
                    logger.info("{} write seqid {} failed: ", name(), entry.sequenceId, channelFuture.cause());
                    tryNotifyWithSequenceID(entry.sequenceId, error_types.ERR_TIMEOUT, false);
                }
            }
        });
    }

    private ScheduledFuture addTimer(final int seqID, long timeoutInMillseconds) {
        return rpcGroup.schedule(
                new Runnable() {
                    @Override
                    public void run() {
                        tryNotifyWithSequenceID(seqID, error_types.ERR_TIMEOUT, true);
                    }
                },
                timeoutInMillseconds,
                TimeUnit.MILLISECONDS
        );
    }

    final class DefaultHandler extends SimpleChannelInboundHandler<RequestEntry> {
        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            logger.warn("Channel {} for session {} is inactive", ctx.channel().toString(), name());
            markSessionDisconnect();
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            logger.info("Channel {} for session {} is active", ctx.channel().toString(), name());
            if (needAuthConnection() && !isAuthed()) {
                logger.info("Session {} needs auth", name());
                markSessionNegotiation(ctx.channel());
            } else {
                markSessionConnected(ctx.channel());
            }
        }

        @Override
        public void channelRead0(ChannelHandlerContext ctx, final RequestEntry msg) {
            logger.debug("{}: handle response with seqid({})", name(), msg.sequenceId);
            if (msg.callback != null) {
                msg.callback.run();
            } else {
                logger.warn("{}: seqid({}) has no callback, just ignore the response", name(), msg.sequenceId);
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            logger.warn("got exception in inbound handler {} for session {}: ",
                    ctx.channel().toString(),
                    name(),
                    cause);
            ctx.close();
        }
    }

    // security
    private boolean needAuthConnection() {
        return openAuth;
    }

    private boolean isAuthed() {
        return negoStatus == negotiation_status.SASL_SUCC;
    }

    private void startNegotiation() {
        negotiation_message msg = new negotiation_message();
        msg.status = negotiation_status.SASL_LIST_MECHANISMS;

        sendNegoMsg(msg);

        logger.info("{}: start negotiation", name());
    }

    private void sendNegoMsg(negotiation_message msg) {
        final RequestEntry entry = new ReplicaSession.RequestEntry();
        entry.sequenceId = seqId.getAndIncrement();
        entry.op = new negotiate_operator(msg);
        entry.callback = new SaslRecvHandler((negotiate_operator) entry.op);
        entry.timeoutTask = addTimer(entry.sequenceId, 1000);
        pendingResponse.put(new Integer(entry.sequenceId), entry);

        write(entry, fields);
    }

    private class Action implements PrivilegedExceptionAction {
        @Override
        public Object run() throws Exception {
            return null;
        }
    }

    private class SaslRecvHandler implements Runnable {
        negotiate_operator op;

        SaslRecvHandler(negotiate_operator op) {
            this.op = op;
        }

        @Override
        public void run() {
            try {
                if (op.rpc_error.errno != error_types.ERR_OK) throw new ReplicationException(op.rpc_error.errno);
                handleResp();
            } catch (Exception e) {
                // e.printStackTrace();
                logger.error(e.toString());
            }
        }

        private void handleResp() throws Exception {
            final negotiation_message resp = op.get_response();

            if (resp == null) {
                logger.error("SaslRecvHandler received a null response, abandon it");
                return;
            }

            final negotiation_message msg = new negotiation_message();
            switch (resp.status) {
                case INVALID:
                    throw new Exception("Received a response which status is INVALID");
                case SASL_LIST_MECHANISMS_RESP:
                    Subject.doAs(
                            subject,
                            new Action() {
                                public Object run() throws Exception {
                                    String[] mechanisms = new String[expectedMechanisms.size()];
                                    expectedMechanisms.toArray(mechanisms);
                                    saslClient =
                                            Sasl.createSaslClient(
                                                    mechanisms, null, serviceName, serviceFqdn, props, cbh);
                                    logger.info("Select mechanism: {}", saslClient.getMechanismName());
                                    msg.status = negotiation_status.SASL_SELECT_MECHANISMS;
                                    msg.msg = new blob(saslClient.getMechanismName().getBytes());
                                    return null;
                                }
                            });
                    break;
                case SASL_SELECT_MECHANISMS_OK:
                    Subject.doAs(
                            subject,
                            new Action() {
                                public Object run() throws Exception {
                                    msg.status = negotiation_status.SASL_INITIATE;
                                    if (saslClient.hasInitialResponse()) {
                                        msg.msg = new blob(saslClient.evaluateChallenge(new byte[0]));
                                    } else {
                                        msg.msg = new blob(new byte[0]);
                                    }
                                    return null;
                                }
                            });

                    break;
                case SASL_CHALLENGE:
                    Subject.doAs(
                            subject,
                            new Action() {
                                public Object run() throws Exception {
                                    msg.status = negotiation_status.SASL_RESPONSE;
                                    msg.msg = new blob(saslClient.evaluateChallenge(resp.msg.data));
                                    return null;
                                }
                            });
                    break;
                case SASL_SUCC:
                    markSessionConnected(fields.nettyChannel);
                    negoStatus = negotiation_status.SASL_SUCC; // After succeed, the authentication is permanent in this session
                    return;
                case SASL_AUTH_FAIL:
                    //throw new Exception("Received SASL_AUTH_FAIL");
                    startNegotiation();
                    return;
                default:
                    throw new Exception("Received an unknown response, status " + resp.status);
            }

            sendNegoMsg(msg);
        }
    }

    // for test
    ConnState getState() {
        return fields.state;
    }

    interface MessageResponseFilter {
        public boolean abandonIt(error_types err, TMessage header);
    }

    MessageResponseFilter filter = null;

    final private ConcurrentHashMap<Integer, RequestEntry> pendingResponse = new ConcurrentHashMap<Integer, RequestEntry>();
    final private AtomicInteger seqId = new AtomicInteger(0);

    final private Queue<RequestEntry> pendingSend = new LinkedList<RequestEntry>();

    private final static class VolatileFields {
        public ConnState state = ConnState.DISCONNECTED;
        public Channel nettyChannel = null;
    }

    private volatile VolatileFields fields = new VolatileFields();

    private rpc_address address;
    private Bootstrap boot;
    private EventLoopGroup rpcGroup;

    // security
    private boolean openAuth;
    private String serviceName; // used for SASL authentication
    private String serviceFqdn; // name used for SASL authentication
    private CallbackHandler cbh = null; // Don't need handler for GSSAPI
    private SaslClient saslClient;
    private negotiation_status negoStatus = negotiation_status.INVALID;
    private final HashMap<String, Object> props = new HashMap<String, Object>();
    private LoginContext loginContext = null;
    private final Subject subject;
    // TODO: read expected mechanisms from config file
    private static final List<String> expectedMechanisms =
            new ArrayList<String>(Collections.singletonList("GSSAPI"));

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ReplicaSession.class);
}
