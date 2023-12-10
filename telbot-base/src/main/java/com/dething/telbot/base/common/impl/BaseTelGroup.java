package com.dething.telbot.base.common.impl;

import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.base.common.ITelGroup;
import com.dething.telbot.base.entity.Group;
import com.dething.telbot.base.entity.User;
import com.dething.telbot.base.manager.BotGroupAdminManager;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

public abstract class BaseTelGroup<T> extends HandlerList implements ITelGroup<T> {
    /**
     * 群组数据
     */
    protected T taskData;

    protected ITelBot telBot;

    protected AbsSender absSender;


    protected Group group;

    protected BotGroupAdminManager groupAdmin;


    /**
     * 消息处理对象列表
     */

    public BaseTelGroup(Group group, ITelBot telBot){
        this.absSender = telBot.getAbsSender();
        this.telBot = telBot;
        this.group = group;
        this.groupAdmin = new BotGroupAdminManager(group);

        this.initialize();
    }

    /**
     * 初始化函数
     */
    protected void initialize(){
        this.initHandlerList();
        this.initTaskData();
    }

    public int getId(){
        return this.telBot.getId();
    }

    public ITelBot getTelBot() {
        return telBot;
    }

    abstract protected void initHandlerList();

    abstract protected void initTaskData();

    @Override
    public T getTaskData() {
        return this.taskData;
    }

    @Override
    public BotGroupAdminManager getAdminManager(){
        return this.groupAdmin;
    }


    @Override
    public Group getGroup(){
        return this.group;
    }

    @Override
    public User getOwnerUser(){
        return this.telBot.getUser(this.group.getOwner());
    }

    @Override
    public void restart(String startTime){

    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) throws TelegramApiException {
        return this.absSender.execute(method);
    }


    /**
     * 通知消息
     * @param text
     */
    public void notifyMessage(String text){
        SendMessage message = SendMessage.builder()
                .text(text)
                .chatId(group.getChatId())
                .build();
        try{
            this.absSender.execute(message);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
