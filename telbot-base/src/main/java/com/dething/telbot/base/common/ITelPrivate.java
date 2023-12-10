package com.dething.telbot.base.common;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * 群组接口
 */
public interface ITelPrivate {
    void onMessage(Update update);
}
