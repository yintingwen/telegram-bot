package com.dething.telbot.newBase.enums;

public enum GroupStatusEnum implements IEnumGetter<Integer> {
    // 离线
    OFFLINE(0),
    // 在线
    ONLINE(1),
    // 禁用
    DISABLE(2);

    final int value;

    GroupStatusEnum(int i) {
        this.value = i;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
