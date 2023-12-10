package com.dething.telbot.adsbot.parser.personal;

import com.dething.telbot.newBase.command.BotCommandContext;
import com.dething.telbot.newBase.command.IBotCommandParserWithId;
import lombok.Getter;

@Getter
public class InstructionsParser implements IBotCommandParserWithId {
    String id = "personal:text:instructions";

    @Override
    public boolean parse(BotCommandContext context) {
        return context.getText().equals("详细说明书");
    }

    @Override
    public void run(BotCommandContext context) {
        context.sendTemplateMessage("/ads/helper.ftl", null);
    }
}
