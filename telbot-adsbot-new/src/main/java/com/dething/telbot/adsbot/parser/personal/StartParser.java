package com.dething.telbot.adsbot.parser.personal;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.dething.telbot.newBase.bot.ITelBot;
import com.dething.telbot.newBase.command.BotCommandContext;
import com.dething.telbot.newBase.command.IBotCommandParserWithId;
import com.dething.telbot.newBase.util.TelegramBotUtil;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;


import java.lang.reflect.Type;
import java.util.List;

@Getter
public class StartParser implements IBotCommandParserWithId {
    String id = "personal:text:start";

    @Override
    public boolean parse(BotCommandContext context) {
        return context.getText().equals("/start") || context.getText().equals("开始");
    }

    @Override
    public void run(BotCommandContext context) {
        ITelBot telBot = context.getBot();

        String inlineKeyboardString = "[" +
                "[{\"text\":\"开通账户\",\"callbackData\":\"shiyong\"}]," +
                "[{\"text\":\"点击这里把机器人加进群\",\"url\":\"https://t.me/"+ telBot.getData().getAccount()+"?startgroup=joingroup)\"}]]";

        context.sendTextMessage("我是广告机器人", TelegramBotUtil.createInlineKeyboard(inlineKeyboardString));
        context.sendTextMessage("--", createKeyboard(context));
    }

    public ReplyKeyboardMarkup createKeyboard (BotCommandContext context) {
        String ownerKeyboardString = "[[\"试用\",\"开始\",\"到期时间\"],[\"详细说明书\",\"充值\", \"续费\"]]";
        String notOwnerKeyboardString = "[[\"开通账户\", \"开始\", \"开始时间\", \"详细说明书\"]]";
        // 隐私机器人。并且是非拥有者
        if (context.isNonOwner()) {
            return TelegramBotUtil.createReplyKeyboard(notOwnerKeyboardString);
        } else {
            return TelegramBotUtil.createReplyKeyboard(ownerKeyboardString);
        }
    }
}
