package com.dething.telbot.base.util;

import freemarker.template.Template;
import lombok.NonNull;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.StringWriter;
import java.util.Map;


public class TemplateMessageUtil {
    static void send (TelegramLongPollingBot bot, String templatePath, Map<String, Object> paramMap, @NonNull Long chatId) {
        try{
            StringWriter out = new StringWriter();
            Template template = FreeMarkerUtil.getTemplate(templatePath);
            template.process(paramMap, out);
            String text = out.toString();
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(text);
            message.enableHtml(true);
            bot.execute(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
