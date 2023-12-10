package com.dething.cloud.common.launch.log.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("kgframework_log_error")
public class LogError extends LogAbstract implements Serializable {

	private static final long serialVersionUID = -994323457137564529L;

	private String stackTrace;

	private String exceptionName;

	private String message;

	private String fileName;

	private Integer lineNumber;

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String toString() {
		return "LogError(stackTrace=" + getStackTrace() + ", exceptionName=" + getExceptionName() + ", message="
				+ getMessage() + ", fileName=" + getFileName() + ", lineNumber=" + getLineNumber() + ")";
	}

	public String getStackTrace() {
		return this.stackTrace;
	}

	public String getExceptionName() {
		return this.exceptionName;
	}

	public String getMessage() {
		return this.message;
	}

	public String getFileName() {
		return this.fileName;
	}

	public Integer getLineNumber() {
		return this.lineNumber;
	}
}
