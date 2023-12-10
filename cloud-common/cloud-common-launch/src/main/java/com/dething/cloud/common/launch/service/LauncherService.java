package com.dething.cloud.common.launch.service;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.Ordered;

public interface LauncherService extends Ordered, Comparable<LauncherService> {
	default int getOrder() {
		return Integer.MIN_VALUE;
	}

	default int compareTo(LauncherService o) {
		return Integer.compare(getOrder(), o.getOrder());
	}

	void launcher(SpringApplicationBuilder paramSpringApplicationBuilder, String paramString1, String paramString2);
}
