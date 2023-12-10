package com.dething.cloud.common.launch.log.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.dething.cloud.common.launch.log.annotation.ApiLog;
import com.dething.cloud.common.launch.log.publisher.ApiLogPublisher;
import org.springframework.stereotype.Component;


/**
 * 操作日志使用 spring event异步入库
 *
 */
@Slf4j
@Aspect
@Component
public class ApiLogAspect {

	@Around("@annotation(apiLog)")
	public Object around(ProceedingJoinPoint point, ApiLog apiLog) throws Throwable {
		//获取类名
		String className = point.getTarget().getClass().getName();
		//获取方法
		String methodName = point.getSignature().getName();
		// 发送异步日志时间
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长（毫秒）
		long time = System.currentTimeMillis() - beginTime;
		//执行日志
		ApiLogPublisher.publishEvent(methodName, className, apiLog, time);
		return result;
	}

}
