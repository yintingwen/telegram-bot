package com.dething.telbot.newBase.enums;

public enum GroupAdminEnum implements IEnumGetter<Integer> {
    admin(0),
    operator(1);

    private final int value;

    GroupAdminEnum(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
};