package com.dething.telbot.adsbot.manager;

import com.dething.telbot.adsbot.domain.AdsTelBot;
import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.base.entity.Bot;
import com.dething.telbot.base.manager.BotManager;
import com.dething.telbot.newBase.service.IBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Component
@EnableScheduling
@DependsOn("springUtil")
public class AdsBotManager extends BotManager implements ApplicationRunner {
    @Autowired
    IBotService botService;

    public int getBotType() { return 2; }

    public ITelBot createTelBot(Bot bot, DefaultBotOptions botOptions){
        return new AdsTelBot(bot, botOptions);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
