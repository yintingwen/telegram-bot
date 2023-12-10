package com.dething.telbot.newBase.command.group;

import com.dething.telbot.newBase.command.BotCommandContext;
import com.dething.telbot.newBase.command.IBotCommandParser;
import lombok.Data;

@Data
public class GroupParser implements IBotCommandParser {
    @Override
    public boolean parse(BotCommandContext context) {

        return false;
    }

    @Override
    public void run(BotCommandContext context) {

    }
}
