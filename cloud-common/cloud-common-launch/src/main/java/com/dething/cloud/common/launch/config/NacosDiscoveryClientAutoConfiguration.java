package com.dething.cloud.common.launch.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.CommonsClientAutoConfiguration;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.discovery.NacosWatch;

@Configuration
@ConditionalOnNacosDiscoveryEnabled
@AutoConfigureBefore({SimpleDiscoveryClientAutoConfiguration.class, CommonsClientAutoConfiguration.class})
public class NacosDiscoveryClientAutoConfiguration {

    public NacosDiscoveryClientAutoConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean
    public NacosDiscoveryProperties nacosProperties() {
        return new NacosDiscoveryProperties();
    }
    /**
     @Bean
     public DiscoveryClient nacosDiscoveryClient(NacosDiscoveryProperties discoveryProperties) {
     return new NacosDiscoveryClient(discoveryProperties);
     }
     **/

//    @Bean
//    @ConditionalOnMissingBean
//    @ConditionalOnProperty(value = {"spring.cloud.nacos.discovery.watch.enabled"}, matchIfMissing = true)
//    public NacosWatch nacosWatch(NacosDiscoveryProperties nacosDiscoveryProperties) {
//        //原来的元数据全部清空
//        // nacosDiscoveryProperties.setMetadata(new HashMap<>());
//        //更改服务详情中的元数据，增加服务注册时间
//        Object obj = PropertiesUtils.getCommonYml("version");
//        String version = obj == null ? "" : (String) obj;
//        nacosDiscoveryProperties.getMetadata().put("version", version);
//        return new NacosWatch(nacosDiscoveryProperties);
//    }
}

