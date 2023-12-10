package com.dething.cloud.common.launch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.function.Function;
import java.util.stream.Collectors;

//import com.dething.cloud.common.launch.config.PropertiesUtils;
import com.dething.cloud.common.core.util.PropertiesUtil;
import com.dething.cloud.common.launch.service.LauncherService;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class LaunchApplication {
	
	private static String nacos_url;
	
	private static String nacos_std;
	
	private static String nacos_version;
	
	private static String version;

	private static String prefix;

	public static ConfigurableApplicationContext run(String appName, Class source, String... args) {
		PropertiesUtil properties = PropertiesUtil.load("application.yml");

		version = properties.getProperty("version", "");
		nacos_url = properties.getProperty("nacos.url", "127.0.0.1:8848");
		nacos_std = properties.getProperty("nacos.std", "127.0.0.1:8858");
		nacos_version = properties.getProperty("nacos.version", "1.2.0");
		prefix = properties.getProperty("nacos.prefix", "dething");

		System.out.println("------------nacos.prefix = " + prefix);
         
		return createSpringApplicationBuilder(appName, source, args).run(args);
	}

	@SuppressWarnings("unchecked")
	public static SpringApplicationBuilder createSpringApplicationBuilder(String appName, Class source, String... args) {
		String profile;
		PropertiesUtil propertiesUtils = PropertiesUtil.load("application.yml");
		Assert.hasText(appName, "[appName]服务名不能为空");

		StandardEnvironment standardEnvironment = new StandardEnvironment();
		MutablePropertySources propertySources = standardEnvironment.getPropertySources();
		propertySources.addFirst((PropertySource) new SimpleCommandLinePropertySource(args));
		propertySources.addLast(
				(PropertySource) new MapPropertySource("systemProperties", standardEnvironment.getSystemProperties()));
		propertySources.addLast((PropertySource) new SystemEnvironmentPropertySource("systemEnvironment",
				standardEnvironment.getSystemEnvironment()));

		String[] activeProfiles = standardEnvironment.getActiveProfiles();

		List<String> profiles = Arrays.asList(activeProfiles);

		List<String> presetProfiles = new ArrayList<>(Arrays.asList(new String[] { "dev", "test", "prod" }));

		presetProfiles.retainAll(profiles);

		List<String> activeProfileList = new ArrayList<>(profiles);
		Function<Object[], String> joinFun = StringUtils::arrayToCommaDelimitedString;
		SpringApplicationBuilder builder = new SpringApplicationBuilder(new Class[] { source });

		if (activeProfileList.isEmpty()) {
//			profile = "dev";
			String active = propertiesUtils.getProperty("spring.profiles.active");
			profile = (active!=null) ? active : "dev";
			activeProfileList.add(profile);
			builder.profiles(new String[] { profile });
		} else if (activeProfileList.size() == 1) {
			profile = activeProfileList.get(0);
		} else {

			throw new RuntimeException("同时存在环境变量:[" + StringUtils.arrayToCommaDelimitedString(activeProfiles) + "]");
		}
		String startJarPath = LaunchApplication.class.getResource("/").getPath().split("!")[0];
		String activePros = joinFun.apply(activeProfileList.toArray());
		System.out.println(
				String.format("----启动中，读取到的环境变量:[%s]，jar地址:[%s]----", new Object[] { activePros, startJarPath }));
		Properties props = System.getProperties();

		props.setProperty("spring.application.name", appName);
		props.setProperty("spring.profiles.active", profile);
		props.setProperty("info.version", nacos_version);
		props.setProperty("info.desc", appName);
		props.setProperty("dething.env", profile);
		props.setProperty("dething.name", appName);
		props.setProperty("dething.is-local", String.valueOf(isLocalDev()));
		props.setProperty("dething.dev-mode", profile.equals("prod") ? "false" : "true");
		props.setProperty("dething.service.version", nacos_version);
		props.setProperty("spring.main.allow-bean-definition-overriding", "true");
		props.setProperty("spring.cloud.nacos.discovery.server-addr", nacos_url);
		props.setProperty("spring.cloud.nacos.config.server-addr", nacos_url);
		props.setProperty("spring.cloud.nacos.config.prefix", prefix);
		props.setProperty("spring.cloud.nacos.config.file-extension", "yaml");
		props.setProperty("spring.cloud.sentinel.transport.dashboard", nacos_std);
		props.setProperty("spring.cloud.alibaba.seata.tx-service-group", appName.concat("-group"));

		List<LauncherService> launcherList = new ArrayList<>();
		ServiceLoader.<LauncherService> load(LauncherService.class).forEach(launcherList::add);
		((List) launcherList.stream().sorted(Comparator.comparing(LauncherService::getOrder))
				.collect(Collectors.toList()))
						.forEach(launcherService -> ((LauncherService) launcherService).launcher(builder, appName, profile));
		return builder;
	}

	public static boolean isLocalDev() {
		String osName = System.getProperty("os.name");
		return (StringUtils.hasText(osName) && !"LINUX".equals(osName.toUpperCase()));
	}
}
