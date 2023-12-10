package com.dething.telbot.base.util;

import com.dething.telbot.base.entity.MinuteTaskSchedulerConfig;
import lombok.Data;

import java.util.Date;
import java.util.Observer;

@Data
public class MinuteTaskSchedulerResult {
    String taskId;
    Boolean open;
    Observer job;
    Integer interval;
    Long lastTime;

    MinuteTaskSchedulerResult (MinuteTaskSchedulerConfig minuteTaskSchedulerConfig, Observer job) {
        this.open = minuteTaskSchedulerConfig.getOpen();
        this.taskId = minuteTaskSchedulerConfig.getTaskId();
        this.interval = minuteTaskSchedulerConfig.getInterval();
        this.lastTime = minuteTaskSchedulerConfig.getLastTime().getTime();
        this.job = job;
    }
}
