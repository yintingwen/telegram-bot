package com.dething.telbot.newBase.command.personal;

import com.dething.telbot.newBase.command.BotCommandContext;
import com.dething.telbot.newBase.command.IBotCommandParserWithId;
import lombok.Getter;

@Getter
public abstract class StartParser implements IBotCommandParserWithId {
    String id = "personal:text:start";

    @Override
    public boolean parse(BotCommandContext context) {
        return context.getText().equals("/start") || context.getText().equals("开始");
    }

    @Override
    public void run(BotCommandContext context) {
        String text = "您好，我是" + context.getBot().getDuty().getName() + "，请点击下方开通账户后再进行试用";
    }
}
