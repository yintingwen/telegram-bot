package com.dething.telbot.base.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.base.entity.Bot;
import com.dething.telbot.newBase.service.impl.BotService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import java.util.List;

public abstract class BotManager {
//    public abstract ITelBot createTelBot(Bot bot, DefaultBotOptions botOptions);
//
//    public abstract int getBotType();
//
//    public void add(Bot bot) {
//        DefaultBotOptions botOptions = new DefaultBotOptions();
//        botOptions.setProxyType(DefaultBotOptions.ProxyType.NO_PROXY);
//
//        if(bot.getProxyType() != 0){
//            botOptions.setProxyHost(bot.getProxyHost());
//            botOptions.setProxyPort(bot.getProxyPort());
//            botOptions.setProxyType(DefaultBotOptions.ProxyType.values()[bot.getProxyType()]);
//        }
//
//        ITelBot telBot = this.createTelBot(bot, botOptions);
//        this.add(bot.getId(), telBot);
//        telBot.run();
//    }
//
//    public void add(Integer botId, ITelBot bot) {
//        this.managerMap.put(botId, bot);
//    }
//
//    public void add(List<Bot> botList) {
//        for (Bot bot: botList) {
//            this.add(bot);
//        }
//    }
//
//    public void remove(Integer botId) {
//        ITelBot telBot = this.managerMap.get(botId);
//        if (telBot == null) return;
//        this.managerMap.remove(botId);
//    }
//
//    /**
//     * 刷新机器人用户
//     * @param botId 机器人ID
//     * @param userName 用户名
//     */
//    public void refreshBotUser (Integer botId, String userName) {
//        ITelBot telBot = this.managerMap.get(botId);
//        if (telBot == null) return;
//        telBot.flushUser(userName);
//    };
//
//    public void initialize() {
//        BotService botService = SpringUtil.getBean(BotService.class);
//        List<Bot> botList = botService.list(new QueryWrapper<Bot>().eq("type", this.getBotType()));
//        this.add(botList);
//    }
//
//    public void run(ApplicationArguments args) throws Exception {
//        this.initialize();
//    }
}
