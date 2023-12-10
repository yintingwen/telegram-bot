package com.dething.cloud.common.launch.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.dething.cloud.common.launch.props.LaunchProperties;

@Configuration
@Order(-2147483648)
@EnableConfigurationProperties({LaunchProperties.class})
public class LaunchConfiguration {
	
}

