package com.dething.cloud.common.core.util;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {
    private Properties properties;

    private static final Map<String, PropertiesUtil> propertiesMap = new HashMap<>();

    PropertiesUtil(String fileName) {
        ClassPathResource resource = new ClassPathResource(fileName);
        boolean isYml = fileName.endsWith(".yml") || fileName.endsWith(".yaml");
        try {
            if (isYml) {
                YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
                yamlFactory.setResources(resource);
                this.properties = yamlFactory.getObject();
            } else {
                this.properties = new Properties();
                this.properties.load(resource.getInputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getProperty (String key) {
        return this.properties.getProperty(key);
    }

    public String getProperty (String key, String defaultValue) {
        String value = this.properties.getProperty(key);
        return value != null ? value : defaultValue;
    }

    /**
     * 获取实例
     * @param fileName
     * @return
     */
    public static PropertiesUtil load(String fileName) {
        PropertiesUtil propertiesUtils = propertiesMap.get(fileName);
        if (propertiesUtils == null) {
            propertiesMap.put(fileName, new PropertiesUtil(fileName));
        }
        System.out.println(propertiesMap);
        return propertiesMap.get(fileName);
    }
}
