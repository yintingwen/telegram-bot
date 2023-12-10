package com.dething.cloud.common.launch.log.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


@Data
public class LogAbstract implements Serializable {

	private static final long serialVersionUID = -7521702706248778232L;

	@TableId(value = "id", type = IdType.AUTO)
	protected Long id;

	protected String serviceId;
	
	protected String serverIp;
	
	protected String serverHost;
	
	protected String env;
	
	protected String remoteIp;
	
	protected String userAgent;
	
	protected String requestUri;
	
	protected String method;
	
	protected String methodClass;
	
	protected String methodName;
	
	protected String params;
	
	protected String time;
	
	protected String createBy;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date createTime;
}
