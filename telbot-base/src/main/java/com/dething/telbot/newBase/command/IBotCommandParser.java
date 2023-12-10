package com.dething.telbot.newBase.command;

public interface IBotCommandParser {
    /**
     * 解析方法
     * @param context 上下文
     */
    public boolean parse(BotCommandContext context);

    /**
     * 执行方法
     * @param context 上下文
     */
    public void run (BotCommandContext context);
}
