package com.dething.telbot.newBase.command;

public interface IBotCommandBeforeRun extends IBotCommandInterceptor {
    /**
     * 执行方法
     * @param context 上下文
     */
    public boolean run (BotCommandContext context);
}
