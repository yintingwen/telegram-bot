package com.dething.telbot.base.util;

import com.dething.telbot.base.entity.MinuteTaskSchedulerConfig;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

@EnableScheduling
public class MinuteTaskScheduler {
    private static final Map<String, Observer> taskJobMap = new HashMap<>();
    private static final Map<String, HashMap<String, Long>> taskConfigMap = new HashMap<>();

    private static final Map<String, MinuteTaskSchedulerConfig> taskMap = new HashMap<>();

    public static void addTask (MinuteTaskSchedulerConfig minuteTaskSchedulerConfig, Observer job) {

//        taskMap.put(minuteTaskSchedulerConfig.getTaskId(), minuteTaskSchedulerConfig);
    }

    public static void addTask (String taskId ,Observer observer, Integer interval, Long lastMinute) {
        taskJobMap.put(taskId, observer);
        taskConfigMap.put(taskId, new HashMap<String, Long>() {{
            put("interval", (long) interval * 60 * 1000);
            put("lastMinute", lastMinute);
        }});
    }

    public static void removeTask (String taskId) {
        taskJobMap.remove(taskId);
        taskConfigMap.remove(taskId);
    }
    public static void removeTask (MinuteTaskSchedulerConfig minuteTaskSchedulerConfig) {

    }

    // 每小时执行一次，第一个0表示秒，第二个0表示分钟，*表示任意值
    @Scheduled(cron = "0 * * * * *")
    private static void scheduled() {
        taskConfigMap.forEach((taskId, taskConfig) -> {
            long nowDataTime = System.currentTimeMillis();
            long lastDateTime = taskConfig.get("lastMinute");
            long interval = taskConfig.get("interval");
            if ((nowDataTime - lastDateTime) < interval) return;
            taskJobMap.get(taskId).update(null, null);
            taskConfig.put("lastMinute", nowDataTime);
        });
    }
}
