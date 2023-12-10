package com.dething.telbot.newBase.command;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BotCommandGroup {
    String id;

    Set<IBotCommandParser> parserSet = new HashSet<>();

    Map<String, BotCommandGroup> childrenGroup = new HashMap<>();

    public BotCommandGroup(String group) {
        this.id = group;
    }

    public void addParser(IBotCommandParser parser) {
        parserSet.add(parser);
    }
}
