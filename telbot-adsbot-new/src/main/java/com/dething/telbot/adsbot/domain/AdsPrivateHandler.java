package com.dething.telbot.adsbot.domain;

import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.adsbot.service.IBotMediaService;
import com.dething.telbot.base.common.IMessageHandler;
import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.base.common.impl.BasePrivateHandler;


public abstract class AdsPrivateHandler extends BasePrivateHandler implements IMessageHandler {

    protected IBotMediaService botMediaService;
    public AdsPrivateHandler(ITelBot telBot){
        super(telBot);
        botMediaService = SpringUtil.getBean(IBotMediaService.class);
    }
}