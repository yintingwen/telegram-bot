package com.dething.telbot.base.common;

import com.dething.telbot.base.entity.Group;
import com.dething.telbot.base.entity.User;
import com.dething.telbot.base.manager.BotGroupAdminManager;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

/**
 * 群组接口
 */
public interface ITelGroup<T> {
    /**
     * 获取任务数据
     * @return
     */
    T getTaskData();

    Group getGroup();

    /**
     * 获取群组管理员
     * @return
     */
    BotGroupAdminManager getAdminManager();

    /**
     * 返回所属TelBot对象
     * @return
     */
    ITelBot getTelBot();

    /**
     * 获取归属用户
     * @return
     */
    User getOwnerUser();

    void restart(String startTime);

    /**
     * 获取所使用机器人的ID
     * @return
     */
    int getId();


    /**
     * 发送消息接口s
     * @param method
     * @return
     * @param <T>
     * @param <Method>
     * @throws TelegramApiException
     */
    <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) throws TelegramApiException;


    /**
     * 处理消息
     * @param update 接收到的消息体
     */
    void onMessage(Update update);

    /**
     * 通知消息
     * @param text
     */
    void notifyMessage(String text);

}
