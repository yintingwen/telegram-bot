package com.dething.telbot.newBase.command;

import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.base.entity.User;
import com.dething.telbot.newBase.bot.ITelBot;
import com.dething.telbot.newBase.bot.ITelBotSender;
import com.dething.telbot.newBase.bot.TelBot;
import com.dething.telbot.newBase.helper.UserHelper;
import com.dething.telbot.newBase.manager.BotCommandManager;
import com.dething.telbot.newBase.manager.BotManager;
import com.dething.telbot.newBase.manager.BotUserManager;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
public class BotCommandContext {
    Map<String, Object> options = new HashMap<>();

    private final BotCommandManager commandManager;

    private Long chatId;
    private String text;
    private String chatType;
    // 媒体文件
    private String mediaFileId;
    private String mediaType;
    private String messageType;
    // 机器人
    public Integer botId;
    public ITelBot bot;
    // 操作人
    public Long operatorId; // 操作人唯一ID
    private String operatorUserName; // 操作人用户名
    private String operatorAccount;
    public User operatorUser; // 操作人缓存的信息

    public BotCommandContext(BotCommandManager commandManager, Update update, Integer botId) {
        BotManager botManager = SpringUtil.getBean(BotManager.class);
        this.botId = botId;
        this.bot = botManager.getBot(botId);
        this.commandManager = commandManager;
        this.parseUpdate(update);
    }

    private void parseUpdate (Update update) {
        Message message = update.getMessage();
        CallbackQuery callbackQuery = update.getCallbackQuery();
        System.out.println(message.getFrom());

        if (message != null) {
            this.chatId = message.getChatId();
            this.text = message.getText();
            this.operatorId = message.getFrom().getId();
            this.operatorAccount = message.getFrom().getUserName();
            this.operatorUser = UserHelper.getUser(this.botId, this.operatorAccount);
            this.chatType = message.getChat().getType();
            this.messageType = "message";
            this.parseMedia(message);
            return;
        }
        if (update.hasCallbackQuery()) {
            this.chatId = callbackQuery.getMessage().getChatId();
            this.text = callbackQuery.getData();
            this.operatorId = message.getFrom().getId();
            this.operatorAccount = callbackQuery.getFrom().getUserName();
            this.operatorUser = UserHelper.getUser(this.botId, this.operatorAccount);
            this.chatType = callbackQuery.getMessage().getChat().getType();
            this.messageType = "callback";
        }
    }

    public void parseMedia (Message message) {
        if (message.hasPhoto()) {
            this.mediaFileId = message.getPhoto().get(0).getFileId();
            this.mediaType = "image";
        } else if (message.hasVideo()) {
            this.mediaFileId = message.getVideo().getFileId();
            this.mediaType = "video";
        } else if (message.hasAnimation()) {
            this.mediaFileId = message.getAnimation().getFileId();
            this.mediaType = "animation";
        }
    }

    /**
     * 判断是否有文字
     * @return boolean
     */
    public boolean hasText () {
        return this.text != null;
    }

    /**
     * 判断是否有媒体文件
     */
    public boolean hasMedia () {
        return this.mediaFileId != null;
    }

    /**
     * 是否是机器人的非拥有者
     */
    public boolean isNonOwner () {
        return this.bot.isPrivate() && !Objects.equals(this.operatorAccount, this.bot.getOwner());
    }

    /**
     * 重定向到指定的消息处理器
     * @param group 消息处理器组
     */
    public void redirect (String group) {
        throw new BotCommandRedirectException(group);
    }

    public void sendTextMessage(String text) {
        this.bot.sendTextMessage(this.chatId, text);
    }

    public void sendTextMessage(String text, ReplyKeyboard replyMarkup) {
        this.bot.sendTextMessage(this.chatId, text, replyMarkup);
    }

    public void sendTemplateMessage(String templatePath, Map<String, Object> params) {
        this.bot.sendTemplateMessage(this.chatId, templatePath, params);
    }
}
