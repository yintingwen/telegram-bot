package com.dething.telbot.newBase.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Map;

public interface ITelBotSender {
    void sendMessage (SendMessage message);
    void sendTextMessage(Long chatId, String text);
    void sendTextMessage(Long chatId, String text, ReplyKeyboard replyMarkup);
    void sendTemplateMessage(Long chatId, String templatePath, Map<String, Object> params);
}
