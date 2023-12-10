package com.dething.cloud.common.launch.log.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

public class UsualLogEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1150564832769380397L;

	public UsualLogEvent(Map<String, Object> source) {
		super(source);
	}
}
