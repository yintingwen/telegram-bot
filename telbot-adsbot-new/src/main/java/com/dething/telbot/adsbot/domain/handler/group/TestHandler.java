package com.dething.telbot.adsbot.domain.handler.group;

import com.dething.telbot.adsbot.domain.AdsGroup;
import com.dething.telbot.adsbot.domain.AdsGroupHandler;
import com.dething.telbot.base.common.IMessageHandler;
import com.dething.telbot.base.common.ITelGroup;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TestHandler extends AdsGroupHandler implements IMessageHandler {


    public TestHandler(ITelGroup telGroup){
        super(telGroup);
    }

    @Override
    public boolean parse(Update update){
        String command = "测试";

        if(update.getMessage()==null) return false;

        String msg = update.getMessage().getText();

        if(!msg.startsWith(command))  return false;

        this.operator = update.getMessage().getFrom().getUserName();

        return true;
    }

    @Override
    public void execute() {
        if (!this.checkPermission(this.operator)) return;
        AdsGroup adsGroup = (AdsGroup)this.telGroup;
        try{
            adsGroup.testAds();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
