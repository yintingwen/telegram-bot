package com.dething.telbot.base.common.impl;

import com.dething.cloud.common.core.util.EnumUtil;
import com.dething.telbot.base.common.IMessageHandler;
import com.dething.telbot.base.common.ITelGroup;
import com.dething.telbot.base.entity.GroupAdmin;
import com.dething.telbot.base.enums.GroupAdminEnum;
import com.dething.telbot.base.manager.BotGroupAdminManager;

import java.util.Map;

public abstract class BaseGroupHandler<T> implements IMessageHandler {
    protected ITelGroup<T> telGroup = null;
    public BaseGroupHandler(ITelGroup<T> telGroup){
        this.telGroup = telGroup;
    }

    protected String operator = null;

    public void templateMessage(String templatePath, Map<String, Object> paramMap){
        telGroup.getTelBot().templateMessage(templatePath,paramMap,telGroup.getGroup().getChatId());
    }

    protected boolean checkPermission(String operator, GroupAdminEnum permission){
        BotGroupAdminManager groupAdmin = this.telGroup.getAdminManager();
        GroupAdmin adminAccount = groupAdmin.get(operator);
        return adminAccount != null && adminAccount.getPermission() == permission.getValue();
    }

    protected boolean checkPermission(String operator){
        BotGroupAdminManager groupAdmin = this.telGroup.getAdminManager();
        GroupAdmin adminAccount = groupAdmin.get(operator);
        return adminAccount != null && EnumUtil.containsPropertyValue(GroupAdminEnum.class, GroupAdminEnum::getValue, adminAccount.getPermission());
    }

}
