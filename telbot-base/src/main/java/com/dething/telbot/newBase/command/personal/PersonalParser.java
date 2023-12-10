package com.dething.telbot.newBase.command.personal;

import com.dething.telbot.newBase.command.BotCommandContext;
import com.dething.telbot.newBase.command.IBotCommandParser;

public class PersonalParser implements IBotCommandParser {
    @Override
    public boolean parse(BotCommandContext context) {
        if (context.hasMedia()) {
            context.redirect("personal:media:*");
        }
        if (context.hasText()) {
            context.redirect("personal:text:*");
        }
        return false;
    }

    @Override
    public void run(BotCommandContext context) {

    }
}
