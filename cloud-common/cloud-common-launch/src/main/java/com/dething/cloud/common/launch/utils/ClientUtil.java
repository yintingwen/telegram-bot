package com.dething.cloud.common.launch.utils;

import javax.servlet.http.HttpServletRequest;

public class ClientUtil {
	/**
	 * 获取客户端真实ip
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if (ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
     * 获取Ip地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (isUnknownAddress(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isUnknownAddress(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isUnknownAddress(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isUnknownAddress(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (isUnknownAddress(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
    private static boolean isUnknownAddress(String ip) {
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }
}
