package com.dething.telbot.base.enums;
public enum GroupAdminEnum {
    admin(0),
    operator(1);

    private int value;

    GroupAdminEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
};