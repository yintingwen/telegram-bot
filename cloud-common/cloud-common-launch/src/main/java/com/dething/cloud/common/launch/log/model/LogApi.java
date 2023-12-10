package com.dething.cloud.common.launch.log.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;


@TableName("kgframework_log_api")
public class LogApi extends LogAbstract implements Serializable {

	private static final long serialVersionUID = 429100401517509737L;

	private String type;

	private String title;

	public void setType(String type) {
		this.type = type;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String toString() {
		return "LogApi(type=" + getType() + ", title=" + getTitle() + ")";
	}

	public String getType() {
		return this.type;
	}

	public String getTitle() {
		return this.title;
	}
}
