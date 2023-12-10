package com.dething.telbot.base.manager;

public interface IBotManager {
    void flushBotUser(int botId, String userName);

    void addBot(int botId);

    void onRecharge(int userId, float amount);
}
