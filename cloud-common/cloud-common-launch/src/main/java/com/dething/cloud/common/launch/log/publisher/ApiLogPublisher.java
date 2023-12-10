package com.dething.cloud.common.launch.log.publisher;


import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.dething.cloud.common.launch.log.annotation.ApiLog;
import org.springframework.context.ApplicationEvent;

import com.dething.cloud.common.core.util.SpringUtil;
//import com.dething.cloud.tools.WebUtil;

import com.dething.cloud.common.launch.log.event.ApiLogEvent;
import com.dething.cloud.common.launch.log.model.LogAbstract;
import com.dething.cloud.common.launch.log.model.LogAbstractUtil;
import com.dething.cloud.common.launch.log.model.LogApi;

public class ApiLogPublisher {
	 public static void publishEvent(String methodName, String methodClass, ApiLog apiLog, long time) {
//		    HttpServletRequest request = WebUtil.getRequest();
//		    LogApi logApi = new LogApi();
//		    logApi.setType("1");
//		    logApi.setTitle(apiLog.value());
//		    logApi.setTime(String.valueOf(time));
//		    logApi.setMethodClass(methodClass);
//		    logApi.setMethodName(methodName);
//		    LogAbstractUtil.addRequestInfoToLog(request, (LogAbstract)logApi);
//		    Map<String, Object> event = new HashMap<>(16);
//		    event.put("log", logApi);
//		    SpringUtil.publishEvent((ApplicationEvent)new ApiLogEvent(event));
		  }
}
