package com.dething.telbot.newBase.enums;

public enum BotStatusEnum implements IEnumGetter<Integer> {
    // 运行中
    RUNNING(1),
    // 停止
    STOP(0);

    int value;

    BotStatusEnum(int i) {
        this.value = i;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
