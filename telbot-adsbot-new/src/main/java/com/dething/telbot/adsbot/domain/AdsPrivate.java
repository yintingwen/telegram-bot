package com.dething.telbot.adsbot.domain;

import com.dething.telbot.adsbot.domain.handler.personal.AdsAddHandler;
import com.dething.telbot.adsbot.domain.handler.personal.InstructionsHandler;
import com.dething.telbot.adsbot.domain.handler.personal.StartHandler;
import com.dething.telbot.adsbot.domain.handler.personal.UploadHandler;
import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.base.common.ITelPrivate;
import com.dething.telbot.base.common.impl.BaseTelPrivate;
import com.dething.telbot.base.user.ExpireTimeHandlerImpl;
import com.dething.telbot.base.user.RechargeHandlerImpl;
import com.dething.telbot.base.user.TrialHandlerImpl;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AdsPrivate extends BaseTelPrivate implements ITelPrivate {

    /**
     * 构造函数
     */
    public AdsPrivate(ITelBot telBot){
        super(telBot);
    }

    public void onMessage(Update update){
        this.handleMessage(update);
    }


    /**
     * 初始化消息处理对象列表
     */
    @Override
    protected void initHandlerList(){
        this.addMessageHandler(new StartHandler(this.telBot));
        this.addMessageHandler(new TrialHandlerImpl(this.telBot));
        this.addMessageHandler(new ExpireTimeHandlerImpl(this.telBot));
        this.addMessageHandler(new RechargeHandlerImpl(this.telBot));
        this.addMessageHandler(new InstructionsHandler(this.telBot));
        this.addMessageHandler(new UploadHandler(this.telBot));
        this.addMessageHandler(new AdsAddHandler(this.telBot));
    }
}
