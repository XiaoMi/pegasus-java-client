// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.tools;

/** Created by sunweijie@xiaomi.com on 2016/11/20. */
import com.xiaomi.infra.pegasus.apps.rrdb;
import com.xiaomi.infra.pegasus.apps.update_request;
import com.xiaomi.infra.pegasus.apps.update_response;
import com.xiaomi.infra.pegasus.base.gpid;
import com.xiaomi.infra.pegasus.base.rpc_address;
import com.xiaomi.infra.pegasus.operator.rrdb_put_operator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TMessage;
import org.apache.thrift.protocol.TMessageType;
import org.apache.thrift.protocol.TProtocol;
import org.slf4j.Logger;

public class Toollet {
  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Toollet.class);
  private static String PegasusRunScriptPath;

  static {
    try {
      Process process =
          Runtime.getRuntime().exec(new String[] {"bash", "-c", "ps aux | grep pegasus_server"});
      process.waitFor();
      BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String line;
      int process_id = -1;
      while ((line = input.readLine()) != null) {
        if (line.contains("pegasus_server") && line.contains("meta")) {
          String[] words = line.split("\\s+");
          if (words.length > 1) {
            try {
              process_id = Integer.valueOf(words[1]);
            } catch (Throwable ex) {
            }
          }
          if (process_id == -1) {
            logger.warn("can not get process id from {}", line);
            throw new IllegalArgumentException("Toollet initialize");
          }
          break;
        }
      }

      String get_pegasus_dir = String.format("readlink /proc/%d/cwd", process_id);
      process = Runtime.getRuntime().exec(get_pegasus_dir);
      process.waitFor();
      input = new BufferedReader(new InputStreamReader(process.getInputStream()));
      line = input.readLine().trim();
      PegasusRunScriptPath = line + "/../../";
      input.close();

    } catch (IOException ex) {
      logger.warn(ex.getMessage());
    } catch (InterruptedException e) {
      logger.warn(e.getMessage());
    }
  }

  public static boolean tryExecuteCommand(String command) {
    try {
      Process process = Runtime.getRuntime().exec(new String[] {"bash", "-c", command});
      int result = process.waitFor();
      if (0 != result) {
        logger.warn("exec command {} failed, error code {}", command, result);
        return false;
      }
      return true;
    } catch (IOException e) {
      logger.warn(e.getMessage());
      return false;
    } catch (InterruptedException e) {
      logger.warn(e.getMessage());
      return false;
    }
  }

  public static boolean closeServer(rpc_address server) {
    String command =
        String.format(
            "netstat -anp 2>/dev/null | "
                + "grep %d | grep LISTEN | "
                + "awk '{print $7}' | "
                + "cut -d \"/\" -f 1 | "
                + "xargs kill -9",
            server.get_port());
    return tryExecuteCommand(command);
  }

  public static boolean tryStartServer(rpc_address server) {
    String role;
    int index;

    if (server.get_port() < 34800) {
      // so this is meta server
      assert server.get_port() > 34600;
      role = "-m";
      index = server.get_port() - 34600;
    } else {
      // so this is replica server
      assert server.get_port() < 34900;
      role = "-r";
      index = server.get_port() - 34800;
    }

    String startCommand =
        String.format(
            "pushd %s && ./run.sh start_onebox_instance %s %d && popd",
            PegasusRunScriptPath, role, index);
    boolean ans = tryExecuteCommand(startCommand);
    try {
      logger.info("sleep for a while for a killed server to recover");
      Thread.sleep(15000);
    } catch (Throwable e) {
      e.printStackTrace();
    }

    return ans;
  }

  public static boolean waitCondition(BoolCallable callable, int seconds) {
    do {
      if (callable.call()) return true;
      try {
        Thread.sleep(1000l);
      } catch (Throwable e) {
        e.printStackTrace();
      }
      seconds--;
    } while (seconds > 0);
    return false;
  }

  public interface BoolCallable {
    boolean call();
  }

  public static class test_operator extends rrdb_put_operator {
    public test_operator(gpid gpid, update_request request) {
      super(gpid, "", request, 0);
    }

    public void send_data(org.apache.thrift.protocol.TProtocol oprot, int seqid) throws TException {
      TMessage msg = new TMessage("RPC_RRDB_RRDB_TEST_PUT", TMessageType.CALL, seqid);
      oprot.writeMessageBegin(msg);
      rrdb.put_args put_args = new rrdb.put_args(req);
      put_args.write(oprot);
      oprot.writeMessageEnd();
    }

    public void recv_data(TProtocol iprot) throws TException {
      rrdb.put_result result = new rrdb.put_result();
      result.read(iprot);
      if (result.isSetSuccess()) resp = result.success;
      else
        throw new org.apache.thrift.TApplicationException(
            org.apache.thrift.TApplicationException.MISSING_RESULT, "put failed: unknown result");
    }

    private update_request req;
    private update_response resp;
  }
}
