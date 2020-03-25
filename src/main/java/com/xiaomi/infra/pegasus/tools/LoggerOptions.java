package com.xiaomi.infra.pegasus.tools;

import org.apache.logging.log4j.Level;

public class LoggerOptions {
  private Level rollingLogLevel = Level.ALL;
  private boolean enablePegasusCustomLog = true;
  // PatternLayout
  private String layoutPattern = "%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n";
  // DeleteAction: the action of delete one log file
  // deleteFileAge means reserve time, P7D means 7 days, detail see "Duration.class"
  private String deleteFileAge = "P7D";
  private String deleteFilePath = "log/pegasus";
  private String deleteFileNamePattern = "pegasus.client.log*";
  // RolloverStrategy: the reserve file number
  private String maxFileNumber = "10";
  private String minFileNumber = "1";
  // SizeBasedTriggeringPolicy
  private String singleFileSize = "102400";
  // RollingFileAppender
  private String rollingFileAppenderName = "pegasusRolling-" + System.currentTimeMillis();
  private String rollingFileSaveName = "log/pegasus/pegasus.client.log";
  private String rollingFileSavePattern = "log/pegasus/pegasus.client.log.%d{yyyy-MM-dd.HH:mm:ss}";

  public LoggerOptions setEnablePegasusCustomLog(boolean enablePegasusCustomLog) {
    this.enablePegasusCustomLog = enablePegasusCustomLog;
    return this;
  }

  public void setRollingFileSaveName(String rollingFileSaveName) {
    if (rollingFileSaveName.endsWith("/")) {
      throw new IllegalArgumentException("The log file name is illegal!");
    }

    this.rollingFileSaveName = rollingFileSaveName;
    String[] fileNameSplit = rollingFileSaveName.split("/");
    String fileName = fileNameSplit[fileNameSplit.length - 1];
    if (rollingFileSaveName.length() == fileName.length()) {
      this.deleteFilePath = "./";
    } else {
      this.deleteFilePath =
          rollingFileSaveName.substring(0, rollingFileSaveName.length() - fileName.length());
    }
    this.deleteFileNamePattern = fileName + "*";
  }

  public boolean isEnablePegasusCustomLog() {
    return enablePegasusCustomLog;
  }

  public Level getRollingLogLevel() {
    return rollingLogLevel;
  }

  public String getLayoutPattern() {
    return layoutPattern;
  }

  public String getDeleteFileAge() {
    return deleteFileAge;
  }

  public String getDeleteFilePath() {
    return deleteFilePath;
  }

  public String getDeleteFileNamePattern() {
    return deleteFileNamePattern;
  }

  public String getMaxFileNumber() {
    return maxFileNumber;
  }

  public String getMinFileNumber() {
    return minFileNumber;
  }

  public String getSingleFileSize() {
    return singleFileSize;
  }

  public String getRollingFileAppenderName() {
    return rollingFileAppenderName;
  }

  public String getRollingFileSaveName() {
    return rollingFileSaveName;
  }

  public String getRollingFileSavePattern() {
    return rollingFileSavePattern;
  }
}
