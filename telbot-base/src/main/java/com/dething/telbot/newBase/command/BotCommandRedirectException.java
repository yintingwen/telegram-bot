package com.dething.telbot.newBase.command;

import lombok.Getter;

@Getter
public class BotCommandRedirectException extends RuntimeException{
    private final String target;

    public BotCommandRedirectException(String group) {
        super(group);
        this.target = group;
    }
}
