package com.dething.telbot.base.common;

import com.dething.telbot.base.entity.Duty;
import com.dething.telbot.base.entity.User;
import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Map;

/**
 * 机器人接口
 */
public interface ITelBot {
    int getId();

    String getBotUsername();

    String getContacts();

    AbsSender getAbsSender();

    User createUser(String userName);

    Duty getDuty();

    /**
     * 获取用户信息
     * @param userName
     * @return
     */
    User getUser(String userName);

    /**
     * 刷新用户数据
     * @param userName
     */
    void flushUser(String userName);

    /**
     * 运行机器人
     */
    void run();

    /**
     * 尝试创建群组
     * @param update
     */
    void tryCreateGroup(Update update);

    /**
     * 创建群组
     * @param chatId
     * @param user
     */
    ITelGroup createGroup(long chatId, User user);
    void templateMessage(String templatePath, Map<String, Object> paramMap, @NonNull Long chatId);

    void onRecharge(int userId, float amount);
}
