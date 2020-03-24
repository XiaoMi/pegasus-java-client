package com.xiaomi.infra.pegasus.tools;

import java.nio.charset.Charset;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Filter.Result;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.RolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.action.Action;
import org.apache.logging.log4j.core.appender.rolling.action.DeleteAction;
import org.apache.logging.log4j.core.appender.rolling.action.Duration;
import org.apache.logging.log4j.core.appender.rolling.action.IfFileName;
import org.apache.logging.log4j.core.appender.rolling.action.IfLastModified;
import org.apache.logging.log4j.core.appender.rolling.action.PathCondition;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogWrapper {
  
  public static Logger getRollingFileLogger(String appenderName, Class clazz){
    createRollingFileAppender(appenderName,clazz.getName());
    return LoggerFactory.getLogger(clazz.getName());
  }

  
  private static void createRollingFileAppender(String appenderName,String loggerName) {

    final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
    final org.apache.logging.log4j.core.config.Configuration config = ctx.getConfiguration();
    if ((config.getAppender(appenderName)) != null
        && config.getAppender(appenderName) instanceof RollingFileAppender) {
      return;
    }

    final PatternLayout layout =
        PatternLayout.newBuilder()
            .withCharset(Charset.forName("UTF-8"))
            .withConfiguration(config)
            .withPattern("%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n")
            .build();

    PathCondition lastModified =
        IfLastModified.createAgeCondition(Duration.parse("P7D"), null);
    PathCondition fileNameMatch =
        IfFileName.createNameCondition("jiashuo.log*", "jiashuo.log*", null);
    PathCondition[] conds = new PathCondition[] {fileNameMatch, lastModified};

    DeleteAction action =
        DeleteAction.createDeleteAction("pegasus_log", false, 1, false, null, conds, null, config);

    Action[] actions = new Action[] {action};

    final RolloverStrategy strategy =
        DefaultRolloverStrategy.newBuilder()
            .withMax("2")
            .withMin("1")
            .withCustomActions(actions)
            .build();

    final TriggeringPolicy policy2 = SizeBasedTriggeringPolicy.createPolicy("10");

    final RollingFileAppender appender =
        RollingFileAppender.newBuilder()
            .setName(appenderName)
            .withImmediateFlush(true)
            .withFileName("pegasus_log/jiashuo.log")
            .withFilePattern("pegasus_log/jiashuo.log.%d{yyyy-MM-dd.HH:mm:ss}")
            .setLayout(layout)
            .withPolicy(policy2)
            .withStrategy(strategy)
            .build();

    appender.start();

    config.addAppender(appender);

    AppenderRef ref = AppenderRef.createAppenderRef(appenderName, null, null);
    AppenderRef[] refs = new AppenderRef[]{ref};

    LoggerConfig loggerConfig = LoggerConfig.createLogger("false", Level.ALL, loggerName,
        "true", refs, null, config, null);
    loggerConfig.addAppender(appender, null, null);
    config.addLogger(loggerName, loggerConfig);
    ctx.updateLoggers(config);
  }

    public static void main(String[] args) throws InterruptedException {
      Logger logger1 = LoggerFactory.getLogger(LogWrapper.class);
        //Logger logger = LogWrapper.getRollingFileLogger("RollingFile2",LogWrapper.class);
        int n = 100;
        while (n-- > 0) {
          Thread.sleep(1000);
          logger1.warn("OK!");
        }
    }

}
