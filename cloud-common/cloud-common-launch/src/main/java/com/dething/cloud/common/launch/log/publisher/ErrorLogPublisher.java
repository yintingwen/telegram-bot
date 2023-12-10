package com.dething.cloud.common.launch.log.publisher;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationEvent;

//import com.dething.cloud.tools.Func;
//import com.dething.cloud.common.core.util.SpringUtil;
//import com.dething.cloud.tools.WebUtil;
//import com.dething.cloud.tools.exception.Exceptions;

import com.dething.cloud.common.launch.log.event.ErrorLogEvent;
import com.dething.cloud.common.launch.log.model.LogAbstract;
import com.dething.cloud.common.launch.log.model.LogAbstractUtil;
import com.dething.cloud.common.launch.log.model.LogError;

public class ErrorLogPublisher {
	public static void publishEvent(Throwable error, String requestUri) {
//		HttpServletRequest request = WebUtil.getRequest();
//		LogError logError = new LogError();
//		logError.setRequestUri(requestUri);
//		if (Func.isNotEmpty(error)) {
//			logError.setStackTrace(Exceptions.getStackTraceAsString(error));
//			logError.setExceptionName(error.getClass().getName());
//			logError.setMessage(error.getMessage());
//			StackTraceElement[] elements = error.getStackTrace();
//			if (Func.isNotEmpty((Object[]) elements)) {
//				StackTraceElement element = elements[0];
//				logError.setMethodName(element.getMethodName());
//				logError.setMethodClass(element.getClassName());
//				logError.setFileName(element.getFileName());
//				logError.setLineNumber(Integer.valueOf(element.getLineNumber()));
//			}
//		}
//		LogAbstractUtil.addRequestInfoToLog(request, (LogAbstract) logError);
//		Map<String, Object> event = new HashMap<>(16);
//		event.put("log", logError);
//		event.put("request", request);
//		SpringUtil.publishEvent((ApplicationEvent) new ErrorLogEvent(event));
	}
}
