package com.dething.telbot.adsbot.domain;

import com.dething.telbot.base.common.IMessageHandler;
import com.dething.telbot.base.common.ITelGroup;
import com.dething.telbot.base.common.impl.BaseGroupHandler;
import com.dething.telbot.base.entity.User;

import java.util.*;

public abstract class AdsGroupHandler extends BaseGroupHandler<Object> implements IMessageHandler {
    public AdsGroupHandler(ITelGroup telGroup){
        super(telGroup);
    }

    protected boolean checkOwner(String sender){
        User owner =  telGroup.getOwnerUser();
        if(!owner.getAccount().equals(sender)){
            telGroup.notifyMessage("仅机器人开通账号可操作");
            return false;
        }

        return true;
    }
}
