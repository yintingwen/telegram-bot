package com.dething.telbot.base.entity;

import lombok.Data;

import java.util.Date;
import java.util.Observer;

@Data
public class MinuteTaskSchedulerConfig {
    String taskId;
    Date lastTime;
    Integer interval;
    Boolean open;
    Observer job;
}
