package com.dething.telbot.adsbot;

import com.dething.cloud.common.launch.LaunchApplication;
import com.dething.cloud.common.launch.constant.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan({ "com.dething.telbot.adsbot.mapper", "com.dething.telbot.base.mapper" })
@EnableFeignClients(value = {AppConstant.BASE_PACKAGES, "com.dething.telbot"})
public class AdsbotApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = LaunchApplication.run(AppConstant.APPLICATION_ADSBOT_NAME, AdsbotApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        log.info("\n----------------------------------------------------------\n\t" +
                "Application is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "Swagger-UI: http://" + ip + ":" + port + path + "/doc.html\n" +
                "----------------------------------------------------------");
    }

}
