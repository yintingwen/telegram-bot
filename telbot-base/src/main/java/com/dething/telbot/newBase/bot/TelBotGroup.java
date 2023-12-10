package com.dething.telbot.newBase.bot;

import com.dething.telbot.base.entity.Group;
import com.dething.telbot.newBase.enums.GroupStatusEnum;

import java.util.Map;

public abstract class TelBotGroup implements ITelGroup {
    Group group;

    GroupStatusEnum status = GroupStatusEnum.OFFLINE;

    public TelBotGroup(Group group){
        this.group = group;

    }

    public Group getData() {
        return this.group;
    }

    public Map<String, Object> getConfig() {
        return this.group.getConfig();
    }

    @Override
    public boolean saveConfig() {
        return false;
    }

    abstract public void online();

    abstract public void offline();
}
