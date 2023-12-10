package com.dething.telbot.newBase.bot;

import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.base.entity.Group;
import com.dething.telbot.newBase.service.impl.GroupService;

public class AdsGroup extends TelBotGroup{
    public AdsGroup(Group group) {
        super(group);
    }

    @Override
    public boolean saveConfig() {
        return false;
    }

    @Override
    public void online() {
        this.group.setOffline(0);
        GroupService groupService = SpringUtil.getBean(GroupService.class);
        groupService.saveOrUpdate(this.group);
    }

    @Override
    public void offline() {
        this.group.setOffline(1);
        GroupService groupService = SpringUtil.getBean(GroupService.class);
        groupService.saveOrUpdate(this.group);
    }
}
