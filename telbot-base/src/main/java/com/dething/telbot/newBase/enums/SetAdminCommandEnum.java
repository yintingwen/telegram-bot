package com.dething.telbot.newBase.enums;

public enum SetAdminCommandEnum implements IEnumGetter<String> {
    unknown("未定义命令"),
    setAdmin("设置权限人"),
    cancelAdmin("取消权限人"),
    setOperator("设置操作人"),
    cancelOperator("取消操作人");

    private final String value;

    SetAdminCommandEnum(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
