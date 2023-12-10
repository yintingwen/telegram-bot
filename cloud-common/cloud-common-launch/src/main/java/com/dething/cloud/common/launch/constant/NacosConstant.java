package com.dething.cloud.common.launch.constant;

public interface NacosConstant {
	public static final String NACOS_ADDR = "127.0.0.1:8848";
	public static final String NACOS_CONFIG_PREFIX = "kg";
	public static final String NACOS_GROUP_SUFFIX = "-group";
	public static final String NACOS_CONFIG_FORMAT = "yaml";
	public static final String NACOS_CONFIG_JSON_FORMAT = "json";
	public static final String NACOS_CONFIG_REFRESH = "true";
	public static final String NACOS_CONFIG_GROUP = "DEFAULT_GROUP";

	static String dataId(String appName, String profile) {
		return dataId(appName, profile, "yaml");
	}

	static String dataId(String appName, String profile, String format) {
		return appName + "-" + profile + "." + format;
	}
}
