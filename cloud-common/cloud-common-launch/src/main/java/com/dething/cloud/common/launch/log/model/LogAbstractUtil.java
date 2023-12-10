package com.dething.cloud.common.launch.log.model;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.dething.cloud.common.launch.props.LaunchProperties;
import com.dething.cloud.common.launch.server.ServerInfo;
//import com.dething.cloud.tools.UrlUtil;
//import com.dething.cloud.tools.WebUtil;

public class LogAbstractUtil {
	public static void addRequestInfoToLog(HttpServletRequest request, LogAbstract logAbstract) {
//		logAbstract.setRemoteIp(WebUtil.getIP(request));
//		logAbstract.setUserAgent(request.getHeader("user-agent"));
//		logAbstract.setRequestUri(UrlUtil.getPath(request.getRequestURI()));
//		logAbstract.setMethod(request.getMethod());
//		logAbstract.setParams(WebUtil.getRequestParamString(request));
//		logAbstract.setCreateBy(SecureUtil.getUserAccount(request));
	}

	public static void addOtherInfoToLog(LogAbstract logAbstract, LaunchProperties properties, ServerInfo serverInfo) {
		logAbstract.setServiceId(properties.getName());
		logAbstract.setServerHost(serverInfo.getHostName());
		logAbstract.setServerIp(serverInfo.getIpWithPort());
		logAbstract.setEnv(properties.getEnv());
		logAbstract.setCreateTime(new Date());
		if (logAbstract.getParams() == null)
			logAbstract.setParams("");
	}
}
