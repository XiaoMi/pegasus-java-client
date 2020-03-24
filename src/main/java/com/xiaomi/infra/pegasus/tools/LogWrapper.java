package com.xiaomi.infra.pegasus.tools;

import java.nio.charset.Charset;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
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
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogWrapper {

  private static PegasusRollingFileLogger pegasusLogger;

  public static Logger getRollingFileLogger(Class clazz) {
    LoggerOptions loggerOptions = new LoggerOptions();
    PegasusRollingFileLogger logger = createRollingFileAppender(loggerOptions);
    return logger.getLogger(clazz.getName());
  }

  // TODO 单例
  private static PegasusRollingFileLogger createRollingFileAppender(LoggerOptions loggerOptions) {
    if (pegasusLogger != null) {
      return pegasusLogger;
    }

    LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false /*todo*/);
    Configuration configuration = loggerContext.getConfiguration();

    if ((configuration.getAppender(loggerOptions.rollingFileAppenderName)) != null
        && configuration.getAppender(loggerOptions.rollingFileAppenderName)
            instanceof RollingFileAppender) {
      return new PegasusRollingFileLogger(loggerContext, loggerOptions.rollingFileAppenderName);
    }

    PatternLayout patternLayout =
        PatternLayout.newBuilder()
            .withCharset(Charset.forName("UTF-8"))
            .withConfiguration(configuration)
            .withPattern(loggerOptions.layoutPattern)
            .build();

    PathCondition lastModified =
        IfLastModified.createAgeCondition(Duration.parse(loggerOptions.deleteAge), null);
    PathCondition fileNameMatch =
        IfFileName.createNameCondition(
            loggerOptions.deleteFileNamePattern, loggerOptions.deleteFileNamePattern, null);
    PathCondition[] pathConditions = new PathCondition[] {fileNameMatch, lastModified};
    DeleteAction action =
        DeleteAction.createDeleteAction(
            loggerOptions.deleteFilePath,
            false,
            1,
            false,
            null,
            pathConditions,
            null,
            configuration);
    Action[] actions = new Action[] {action};

    RolloverStrategy strategy =
        DefaultRolloverStrategy.newBuilder()
            .withMax(loggerOptions.maxFileNumber)
            .withMin(loggerOptions.minFileNumber)
            .withCustomActions(actions)
            .build();

    TriggeringPolicy policy = SizeBasedTriggeringPolicy.createPolicy(loggerOptions.singleFileSize);

    RollingFileAppender rollingFileAppender =
        RollingFileAppender.newBuilder()
            .setName(loggerOptions.rollingFileAppenderName)
            .withImmediateFlush(true)
            .withFileName(loggerOptions.rollingFileSaveName)
            .withFilePattern(loggerOptions.rollingFileSavePattern)
            .setLayout(patternLayout)
            .withPolicy(policy)
            .withStrategy(strategy)
            .build();

    rollingFileAppender.start();

    configuration.addAppender(rollingFileAppender);

    AppenderRef ref =
        AppenderRef.createAppenderRef(loggerOptions.rollingFileAppenderName, null, null);
    AppenderRef[] refs = new AppenderRef[] {ref};

    LoggerConfig loggerConfig =
        LoggerConfig.createLogger(
            false,
            Level.ALL,
            loggerOptions.rollingFileAppenderName,
            "true",
            refs,
            null,
            configuration,
            null);
    loggerConfig.addAppender(rollingFileAppender, null, null);
    configuration.addLogger(loggerOptions.rollingFileAppenderName, loggerConfig);
    loggerContext.updateLoggers(configuration);
    return new PegasusRollingFileLogger(loggerContext, loggerOptions.rollingFileAppenderName);
  }

  public static void main(String[] args) throws InterruptedException {
    Logger loggerLogWrapper = LogWrapper.getRollingFileLogger(LogWrapper.class);
    Logger loggerString = LogWrapper.getRollingFileLogger(String.class);
    int n = 5;
    while (n-- > 0) {
      Thread.sleep(1000);
      loggerLogWrapper.warn("LogWrapper = {}", n);
    }

    n = 5;
    while (n-- > 0) {
      Thread.sleep(1000);
      loggerString.warn("String = {}", n);
    }
  }
}

class LoggerOptions {
  // PatternLayout
  String layoutPattern = "%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n";
  // DeleteAction
  String deleteAge = "PT5S";
  String deleteFilePath = "log/pegasus";
  String deleteFileNamePattern = "pegasus.client.log*";
  // RolloverStrategy
  String maxFileNumber = "5";
  String minFileNumber = "1";
  // SizeBasedTriggeringPolicy
  String singleFileSize = "10";
  // RollingFileAppender
  String rollingFileAppenderName = "pegasusRolling";
  String rollingFileSaveName = "log/pegasus/pegasus.client.log";
  String rollingFileSavePattern = "log/pegasus/pegasus.client.log.%d{yyyy-MM-dd.HH:mm:ss}";
}

class PegasusRollingFileLogger {

  public LoggerConfig loggerConfig;
  public LoggerContext loggerContext;
  public Configuration configuration;

  public PegasusRollingFileLogger(LoggerContext loggerContext, String appenderName) {
    this.loggerContext = loggerContext;
    this.configuration = loggerContext.getConfiguration();
    this.loggerConfig = configuration.getLoggerConfig(appenderName);
  }

  public Logger getLogger(String loggerName) {
    configuration.addLogger(loggerName, loggerConfig);
    loggerContext.updateLoggers(configuration);
    return LoggerFactory.getLogger(loggerName);
  }
}
