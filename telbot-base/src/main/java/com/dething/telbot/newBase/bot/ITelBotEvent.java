package com.dething.telbot.newBase.bot;

public interface ITelBotEvent {
    /**
     * 每日校验用户是否过期
     */
    void onCheckUsersExpireDaily ();

    /**
     * 用户机器人国企
     */
    void onUserExpired ();
}
