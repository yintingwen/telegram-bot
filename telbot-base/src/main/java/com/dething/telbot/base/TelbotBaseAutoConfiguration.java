package com.dething.telbot.base;

import com.dething.telbot.newBase.manager.BotCommandManager;
import com.dething.telbot.newBase.command.group.GroupParser;
import com.dething.telbot.newBase.command.personal.PersonalParser;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.dething.telbot.base.*", "com.dething.telbot.newBase.*" })
@MapperScan("com.dething.telbot.newBase.mapper")
public class TelbotBaseAutoConfiguration {
    @Bean
    public BotCommandManager commandManager() {
        BotCommandManager botCommandManager = new BotCommandManager();
        botCommandManager.addParser("group", new GroupParser());
        botCommandManager.addParser("private", new PersonalParser());
        return botCommandManager;
    }
}
