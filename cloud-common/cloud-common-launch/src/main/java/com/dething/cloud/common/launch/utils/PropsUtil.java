package com.dething.cloud.common.launch.utils;

import java.util.Properties;
import org.springframework.util.StringUtils;

public class PropsUtil {
	public static void setProperty(Properties props, String key, String value) {
		if (StringUtils.isEmpty(props.getProperty(key)))
			props.setProperty(key, value);
	}
}
