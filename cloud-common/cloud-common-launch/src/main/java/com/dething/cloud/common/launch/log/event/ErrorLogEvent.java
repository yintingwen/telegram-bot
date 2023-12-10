package com.dething.cloud.common.launch.log.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

public class ErrorLogEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 86108819015068946L;

	public ErrorLogEvent(Map<String, Object> source) {
		super(source);
	}
}
