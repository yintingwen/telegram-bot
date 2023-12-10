package com.dething.telbot.newBase.util;

import com.alibaba.fastjson2.JSON;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TelegramBotUtil {
    static public ReplyKeyboardMarkup createReplyKeyboard (String json) {
        List<List<String>> replyKeyboard = JSON.parseArray(json, (Type) List.class);
        return TelegramBotUtil.createReplyKeyboard(replyKeyboard);
    }

    static public ReplyKeyboardMarkup createReplyKeyboard (List<List<String>> list) {
        if (list.size() == 0) return null;

        List<KeyboardRow> rows = new ArrayList<>(list.size());
        for (List<String> row : list) {
            KeyboardRow keyboardRow = new KeyboardRow();
            for (String text : row) {
                KeyboardButton button = KeyboardButton.builder().text(text).build();
                keyboardRow.add(button);
            }
            rows.add(keyboardRow);
        }
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setKeyboard(rows);
        markup.setResizeKeyboard(true);
        return markup;
    }

    static public InlineKeyboardMarkup createInlineKeyboard (String json) {
        List<List<Map<String, String>>> inlineKeyboard = JSON.parseArray(json, (Type) List.class);
        return TelegramBotUtil.createInlineKeyboard(inlineKeyboard);
    }

    static public InlineKeyboardMarkup createInlineKeyboard (List<List<Map<String, String>>> list) {
        if (list.size() == 0) return null;

        List<List<InlineKeyboardButton>> rows = new ArrayList<>(list.size());
        for (List<Map<String, String>> row : list) {
            List<InlineKeyboardButton> keyboardRow = new ArrayList<>(row.size());
            for (Map<String, String> map : row) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                if (map.get("text") != null) {
                    button.setText(map.get("text"));
                }
                if (map.get("callbackData") != null) {
                    button.setCallbackData( map.get("callbackData"));
                }
                if (map.get("url") != null) {
                    button.setUrl(map.get("url"));
                }
                keyboardRow.add(button);
            }
            rows.add(keyboardRow);
        }

        InlineKeyboardMarkup Keyboard = new InlineKeyboardMarkup();
        Keyboard.setKeyboard(rows);
        return Keyboard;
    }
}
