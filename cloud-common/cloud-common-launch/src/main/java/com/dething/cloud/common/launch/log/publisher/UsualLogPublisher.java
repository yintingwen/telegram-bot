package com.dething.cloud.common.launch.log.publisher;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationEvent;

import com.dething.cloud.common.core.util.SpringUtil;
//import com.dething.cloud.tools.WebUtil;

import com.dething.cloud.common.launch.log.event.UsualLogEvent;
import com.dething.cloud.common.launch.log.model.LogAbstract;
import com.dething.cloud.common.launch.log.model.LogAbstractUtil;
import com.dething.cloud.common.launch.log.model.LogUsual;

public class UsualLogPublisher {
	public static void publishEvent(String level, String id, String data) {
//		HttpServletRequest request = WebUtil.getRequest();
//		LogUsual logUsual = new LogUsual();
//		logUsual.setLogLevel(level);
//		logUsual.setLogId(id);
//		logUsual.setLogData(data);
//		LogAbstractUtil.addRequestInfoToLog(request, (LogAbstract) logUsual);
//		Map<String, Object> event = new HashMap<>(16);
//		event.put("log", logUsual);
//		event.put("request", request);
//		SpringUtil.publishEvent((ApplicationEvent) new UsualLogEvent(event));
	}
}
