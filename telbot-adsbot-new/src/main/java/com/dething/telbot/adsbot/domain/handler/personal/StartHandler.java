package com.dething.telbot.adsbot.domain.handler.personal;

import com.dething.telbot.adsbot.domain.AdsPrivateHandler;
import com.dething.telbot.base.common.IMessageHandler;
import com.dething.telbot.base.common.ITelBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class StartHandler extends AdsPrivateHandler implements IMessageHandler {

    private long chatId;
    public StartHandler(ITelBot telBot){
        super(telBot);
    }

    @Override
    public boolean parse(Update update) {
        if(update.getMessage()==null) return false;

        String msg = update.getMessage().getText();
        if(msg!=null && (msg.equals("/start") || msg.equals("开始"))){
            this.chatId = update.getMessage().getChatId();
            //开始命令
            return true;
        }
        return false;
    }

    @Override
    public void execute() {
        this.notifyMessage(chatId,"我是广告机器人",createInlineKeyboard());
        this.notifyMessage(chatId,"--",createKeyboard());
    }

    private ReplyKeyboardMarkup createKeyboard() {

        KeyboardButton button0 = KeyboardButton.builder().text("试用").build();
        KeyboardButton button1 = KeyboardButton.builder().text("开始").build();
        KeyboardButton button2 = KeyboardButton.builder().text("到期时间").build();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(button0);
        row1.add(button1);
        row1.add(button2);

        KeyboardButton button3 = KeyboardButton.builder().text("详细说明书").build();
        KeyboardButton button4 = KeyboardButton.builder().text("充值").build();
        KeyboardRow row2 = new KeyboardRow();
        row2.add(button3);
        row2.add(button4);

        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setKeyboard(rows);
        markup.setResizeKeyboard(true);
        return markup;
    }

    // 创建一个包含单个按钮的键盘
    private InlineKeyboardMarkup createInlineKeyboard() {
        InlineKeyboardButton button0 = InlineKeyboardButton.builder().text("点击申请｜试用").callbackData("shiyong").build();
        List<InlineKeyboardButton> row0 = new ArrayList<>();
        row0.add(button0);

        InlineKeyboardButton button1 = InlineKeyboardButton.builder().text("点击这里把机器人加进群").url("https://t.me/"+this.telBot.getBotUsername()+"?startgroup=joingroup").build();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(button1);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row0);
        rows.add(row1);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }
}
