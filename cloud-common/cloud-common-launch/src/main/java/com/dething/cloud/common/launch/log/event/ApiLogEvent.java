package com.dething.cloud.common.launch.log.event;

import java.util.Map;

import org.springframework.context.ApplicationEvent;

public class ApiLogEvent extends ApplicationEvent {

	private static final long serialVersionUID = -792116389217805065L;

	public ApiLogEvent(Map<String, Object> source) {
		super(source);
	}
}
