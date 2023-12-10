package com.dething.telbot.base.common.impl;

import com.dething.telbot.base.common.IMessageHandler;
import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.base.entity.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.io.StringWriter;
import java.util.Date;
import java.util.Map;

public abstract class BasePrivateHandler implements IMessageHandler {

    protected final ITelBot telBot;

    protected final AbsSender absSender;

    public BasePrivateHandler(ITelBot telBot){
        this.telBot = telBot;
        this.absSender = telBot.getAbsSender();
    }

    /**
     * 通过模板发送消息
     * @param chatId
     * @param templatePath
     * @param paramMap
     */
    public void templateMessage(long chatId, String templatePath, Map<String, Object> paramMap){
        telBot.templateMessage(templatePath,paramMap,chatId);
    }

    public void notifyMessage(long chatId, String text){
        SendMessage.SendMessageBuilder message = SendMessage.builder();
        notifyBtnMessage(chatId,text,message);
    }

    public void notifyMessage( long chatId, String text, ReplyKeyboard replyMarkup){
        SendMessage.SendMessageBuilder message = SendMessage.builder().replyMarkup(replyMarkup);
        notifyBtnMessage(chatId,text,message);
    }

    public void notifyBtnMessage(long chatId, String text,  SendMessage.SendMessageBuilder message){
        try{
            this.absSender.execute(message.text(text).chatId(chatId).build());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    protected boolean checkUser(long chatId, String account){
        User user = this.telBot.getUser(account);
        if(user==null){
            this.notifyMessage(chatId,"您还没有开通账号，请点击申请试用按钮");
            return false;
        }
//        Date expireTime = user.getExpireTime();
//        if(expireTime.before(new Date())){
//            this.notifyMessage(chatId,"您的账号已过期，请点击充值进行续费");
//            return false;
//        }

        return true;
    }
}