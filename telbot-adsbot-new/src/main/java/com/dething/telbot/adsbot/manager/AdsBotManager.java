package com.dething.telbot.adsbot.manager;

import com.dething.telbot.adsbot.parser.personal.AdsAddParser;
import com.dething.telbot.adsbot.parser.personal.InstructionsParser;
import com.dething.telbot.adsbot.parser.personal.StartParser;
import com.dething.telbot.adsbot.parser.personal.UploadParser;
import com.dething.telbot.newBase.manager.BotCommandManager;
import com.dething.telbot.newBase.manager.BotManager;
import com.dething.telbot.newBase.command.personal.ExpireTimeParser;
import com.dething.telbot.newBase.command.personal.RechargeParser;
import com.dething.telbot.newBase.command.personal.TrialParser;
import org.springframework.stereotype.Component;

@Component
public class AdsBotManager extends BotManager {
    @Override
    public Integer getBotType() {
        return 2;
    }

    @Override
    public void loadCommandParser(BotCommandManager commandManager) {
        commandManager.addParser(new StartParser()); // 开始
        commandManager.addParser(new InstructionsParser()); // 说明书
        commandManager.addParser(new TrialParser()); // 试用
        commandManager.addParser(new ExpireTimeParser()); // 到期时间
        commandManager.addParser("personal:text:recharge", new RechargeParser()); // 充值
        commandManager.addParser("personal:text:adsAdd", new AdsAddParser()); // 添加广告
        commandManager.addParser("personal:media:upload", new UploadParser()); // 上传媒体文件
    }
}
