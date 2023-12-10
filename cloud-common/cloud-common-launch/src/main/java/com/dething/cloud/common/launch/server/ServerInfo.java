package com.dething.cloud.common.launch.server;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Configuration;

import com.dething.cloud.common.launch.utils.INetUtil;

@Configuration
public class ServerInfo implements SmartInitializingSingleton {
	private final ServerProperties serverProperties;
	private String hostName;
	private String ip;
	private Integer port;
	private String ipWithPort;

	public ServerProperties getServerProperties() {
		return this.serverProperties;
	}

	public String getHostName() {
		return this.hostName;
	}

	public String getIp() {
		return this.ip;
	}

	public Integer getPort() {
		return this.port;
	}

	public String getIpWithPort() {
		return this.ipWithPort;
	}

	@Autowired(required = false)
	public ServerInfo(ServerProperties serverProperties) {
		this.serverProperties = serverProperties;
	}

	public void afterSingletonsInstantiated() {
		this.hostName = INetUtil.getHostName();
		this.ip = INetUtil.getHostIp();
		this.port = this.serverProperties.getPort();
		this.ipWithPort = String.format("%s:%d", new Object[] { this.ip, this.port });
	}
}
