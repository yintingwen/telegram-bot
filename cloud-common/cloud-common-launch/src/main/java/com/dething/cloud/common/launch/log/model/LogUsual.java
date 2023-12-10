package com.dething.cloud.common.launch.log.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("kgframework_log_usual")
public class LogUsual extends LogAbstract implements Serializable {

	private static final long serialVersionUID = 4938964312472544390L;

	private String logLevel;

	private String logId;

	private String logData;

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public void setLogData(String logData) {
		this.logData = logData;
	}

	public String toString() {
		return "LogUsual(logLevel=" + getLogLevel() + ", logId=" + getLogId() + ", logData=" + getLogData() + ")";
	}

	public String getLogLevel() {
		return this.logLevel;
	}

	public String getLogId() {
		return this.logId;
	}

	public String getLogData() {
		return this.logData;
	}
}
