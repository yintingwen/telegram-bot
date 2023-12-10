package com.dething.cloud.common.core.domain;

import java.util.concurrent.ScheduledFuture;

public class ScheduledTask {
    volatile ScheduledFuture<?> future;

    public ScheduledTask(ScheduledFuture<?> future) {
        this.future = future;
    }
}
