package com.dething.telbot.base.common;

import org.telegram.telegrambots.meta.api.objects.Update;


public interface IMessageHandler {

    /**
     * 解析命令
     * @param update
     * @return
     */
    boolean parse(Update update);

    /**
     * 执行命令
     */
    void execute();


}
