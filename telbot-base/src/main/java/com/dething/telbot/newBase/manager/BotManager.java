package com.dething.telbot.newBase.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.newBase.bot.ITelBot;
import com.dething.telbot.base.entity.Bot;
import com.dething.telbot.newBase.events.DayTimeKeeper;
import com.dething.telbot.newBase.service.impl.BotService;
import com.dething.telbot.newBase.bot.TelBot;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.*;

public abstract class BotManager implements ApplicationRunner {
    private final Map<Integer, ITelBot> botMap = new HashMap<>();

    public BotManager () {

    }

    public abstract Integer getBotType();

    public ITelBot getBot (Integer botId) {
        return this.botMap.get(botId);
    }

    abstract public void loadCommandParser (BotCommandManager commandManager);

    public void registerBot (Bot bot) {
        DefaultBotOptions botOptions = new DefaultBotOptions();
        botOptions.setProxyType(DefaultBotOptions.ProxyType.NO_PROXY);

        if(bot.getProxyType()!=0){
            botOptions.setProxyHost(bot.getProxyHost());
            botOptions.setProxyPort(bot.getProxyPort());
            botOptions.setProxyType(DefaultBotOptions.ProxyType.values()[bot.getProxyType()]);
        }

        TelBot telBot = new TelBot(bot, botOptions);
        this.botMap.put(bot.getId(), telBot);
        telBot.run();
    }

    public void loadData () {
        BotService botService = SpringUtil.getBean(BotService.class);
        QueryWrapper<Bot> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", this.getBotType());

        List<Bot> botList = botService.list(queryWrapper);
        if (botList.size() == 0) return;
        botList.forEach(this::registerBot);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BotCommandManager commandManager = SpringUtil.getBean(BotCommandManager.class);
        this.loadData();
        this.loadCommandParser(commandManager);
    }
}
