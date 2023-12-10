package com.dething.telbot.adsbot.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dething.telbot.adsbot.domain.AdsTelBot;
import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.base.entity.Bot;
import com.dething.telbot.base.manager.impl.BotManager;
import com.dething.telbot.newBase.service.IBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
@DependsOn("springUtil")
public class AdsBotManagerOld extends BotManager implements ApplicationRunner {
    @Autowired
    IBotService botService;


    public AdsBotManagerOld() {
        super();
    }

    @Override
    protected ITelBot createTelBot(Bot bot, DefaultBotOptions botOptions){
        return new AdsTelBot(bot, botOptions);
    }


    protected void initialize(){
        QueryWrapper<Bot> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", 2);

        List<Bot> botList = botService.list(queryWrapper);
        this.loadBotList(botList);
    }

    @Scheduled(cron = "0 0 * * * *") // 每小时执行一次，第一个0表示秒，第二个0表示分钟，*表示任意值
    public void timekeeper() {
        System.out.println("timekeeper emit.......");
        // 在这里编写需要执行的任务代码
        LocalDateTime currentDateTime = LocalDateTime.now();
        int currentHour = currentDateTime.getHour();
//        timeKeeper.emit(currentHour);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.initialize();
    }
}
