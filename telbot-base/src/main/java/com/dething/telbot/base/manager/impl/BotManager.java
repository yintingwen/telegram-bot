package com.dething.telbot.base.manager.impl;

import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.base.entity.Bot;
import com.dething.telbot.base.manager.IBotManager;
import com.dething.telbot.newBase.service.IBotService;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BotManager implements IBotManager {
    /**
     * 机器人列表，key=机器人ID
     */
    protected Map<Integer, ITelBot> botMap = new HashMap<>();

    /**
     * 添加机器人
     */
    public void registerBot(Bot bot) {
        DefaultBotOptions botOptions = new DefaultBotOptions();
        botOptions.setProxyType(DefaultBotOptions.ProxyType.NO_PROXY);

        if(bot.getProxyType()!=0){
            botOptions.setProxyHost(bot.getProxyHost());
            botOptions.setProxyPort(bot.getProxyPort());
            botOptions.setProxyType(DefaultBotOptions.ProxyType.values()[bot.getProxyType()]);
        }

        ITelBot telBot = this.createTelBot(bot, botOptions) ;
        botMap.put(bot.getId(), telBot);

        telBot.run();
    }

    /**
     * 刷新机器人用户
     * @param botId
     * @param userName
     */
    public void flushBotUser(int botId, String userName){
        ITelBot telBot = this.botMap.get(botId);
        if(telBot!=null){
            telBot.flushUser(userName);
        }
    }

    /**
     * 增加机器人
     * @param botId
     */
    public void addBot(int botId){
        IBotService botService = null;
        ITelBot telBot = this.botMap.get(botId);
        if(telBot!=null) return;

        Bot bot = botService.getById(botId);
        if(bot!=null){
            this.registerBot(bot);
        }
    }
    public void onRecharge(int userId, float amount){
        this.botMap.forEach((botId, bot)->{
            bot.onRecharge(userId, amount);
        });
    }

    /**
     * 实例化TelBot对象
     * @return
     */
    protected abstract ITelBot createTelBot(Bot bot, DefaultBotOptions botOptions);


    /**
     * 加载机器人列表
     */

    protected void loadBotList(List<Bot> botList){
        if(botList.size()!=0){
            botList.forEach(bot->{
                this.registerBot(bot);
            });
        }
    }
}
