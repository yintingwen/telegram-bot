package com.dething.telbot.adsbot.manager;

import com.dething.telbot.base.entity.Group;
import com.dething.telbot.newBase.bot.AdsGroup;
import com.dething.telbot.newBase.bot.ITelGroup;
import com.dething.telbot.newBase.manager.BotCommandManager;
import com.dething.telbot.newBase.manager.BotGroupManager;
import org.springframework.stereotype.Component;

@Component
public class AdsGroupManager extends BotGroupManager {
    @Override
    public ITelGroup createGroup (Group group) {
        return new AdsGroup(group);
    }

    @Override
    public void loadCommandParser(BotCommandManager commandManager) {
    }
}
