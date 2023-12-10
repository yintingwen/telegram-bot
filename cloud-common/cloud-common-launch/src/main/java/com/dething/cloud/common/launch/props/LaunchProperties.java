package com.dething.cloud.common.launch.props;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.Nullable;

@ConfigurationProperties("kg")
public class LaunchProperties {
	private String env;
	private String name;

	public String getEnv() {
		return this.env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private Boolean isLocal = Boolean.FALSE;

	public Boolean getIsLocal() {
		return this.isLocal;
	}

	public void setIsLocal(Boolean isLocal) {
		this.isLocal = isLocal;
	}

	private final Map<String, String> prop = new HashMap<>();

	public Map<String, String> getProp() {
		return this.prop;
	}

	@Nullable
	public String get(String key) {
		return get(key, null);
	}

	@Nullable
	public String get(String key, @Nullable String defaultValue) {
		String value = this.prop.get(key);
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	@Nullable
	public Integer getInt(String key) {
		return getInt(key, null);
	}

	@Nullable
	public Integer getInt(String key, @Nullable Integer defaultValue) {
		String value = this.prop.get(key);
		if (value != null) {
			return Integer.valueOf(value.trim());
		}
		return defaultValue;
	}

	@Nullable
	public Long getLong(String key) {
		return getLong(key, null);
	}

	@Nullable
	public Long getLong(String key, @Nullable Long defaultValue) {
		String value = this.prop.get(key);
		if (value != null) {
			return Long.valueOf(value.trim());
		}
		return defaultValue;
	}

	@Nullable
	public Boolean getBoolean(String key) {
		return getBoolean(key, null);
	}

	@Nullable
	public Boolean getBoolean(String key, @Nullable Boolean defaultValue) {
		String value = this.prop.get(key);
		if (value != null) {
			value = value.toLowerCase().trim();
			return Boolean.valueOf(Boolean.parseBoolean(value));
		}
		return defaultValue;
	}

	@Nullable
	public Double getDouble(String key) {
		 return getDouble(key, null);
	}

	@Nullable
	public Double getDouble(String key, @Nullable Double defaultValue) {
		String value = this.prop.get(key);
		if (value != null) {
			return Double.valueOf(Double.parseDouble(value.trim()));
		}
		return defaultValue;
	}

	public boolean containsKey(String key) {
		return this.prop.containsKey(key);
	}
}
