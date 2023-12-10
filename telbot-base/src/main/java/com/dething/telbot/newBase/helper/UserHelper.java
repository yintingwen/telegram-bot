package com.dething.telbot.newBase.helper;

import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.base.entity.User;
import com.dething.telbot.newBase.bot.ITelBot;
import com.dething.telbot.newBase.manager.BotUserManager;

public class UserHelper {
    static BotUserManager botUserManager;

    static {
        botUserManager = SpringUtil.getBean(BotUserManager.class);
    }

    /**
     * 根据账户获取用户信息
     * @param botId 机器人ID
     * @param account 账户名
     * @return User
     */
    static public User getUser (int botId, String account) {
        return botUserManager.getUser(botId, account);
    }

    /**
     * 获取机器人拥有者的用户信息
     * @param telBot 机器人实例
     * @return User
     */
    static public User getBotOwnerUser (ITelBot telBot) {
        return botUserManager.getUser(telBot.getData().getId(), telBot.getOwner());
    }

    /**
     * 获取当前会话的拥有者的用户信息
     * 如果是公开机器人，会话拥有者就是当前操作者
     * 如果是私有机器人，会话拥有者始终是机器人拥有者
     * @param telBot 机器人实例
     * @param account 操作的账户
     * @return User
     */
    static public User getSessionOwnerUser (ITelBot telBot, String account) {
        return telBot.isPrivate() ? UserHelper.getBotOwnerUser(telBot) : UserHelper.getUser(telBot.getId(), account);
    }
}
