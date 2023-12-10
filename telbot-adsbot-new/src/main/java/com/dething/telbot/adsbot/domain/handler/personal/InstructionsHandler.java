package com.dething.telbot.adsbot.domain.handler.personal;

import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.adsbot.domain.AdsPrivateHandler;
import com.dething.telbot.base.common.IMessageHandler;
import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.newBase.service.IUserService;
import com.dething.telbot.newBase.service.impl.UserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class InstructionsHandler extends AdsPrivateHandler implements IMessageHandler {

    private long chatId;

    public IUserService userService;

    public InstructionsHandler(ITelBot telBot){
        super(telBot);
        userService = SpringUtil.getBean(UserService.class);
    }

    @Override
    public boolean parse(Update update) {
        if(update.getMessage()==null) return false;

        String text = update.getMessage().getText();
        if(text!=null && text.equals("详细说明书")){
            this.chatId = update.getMessage().getChatId();

            return true;
        }
        return false;
    }

    @Override
    public void execute() {
        this.templateMessage(chatId, "/ads/helper.ftl", null);
    }

}
