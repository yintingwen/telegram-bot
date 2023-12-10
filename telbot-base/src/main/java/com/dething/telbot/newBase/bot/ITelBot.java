package com.dething.telbot.newBase.bot;

import com.dething.telbot.base.entity.Bot;
import com.dething.telbot.base.entity.Duty;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.lang.reflect.Method;
import java.util.Map;

public interface ITelBot extends ITelBotSender, ITelBotData, ITelBotEvent{
    /**
     * 运行
     */
    void run();

    /**
     * 停止
     */
    void stop();
}
