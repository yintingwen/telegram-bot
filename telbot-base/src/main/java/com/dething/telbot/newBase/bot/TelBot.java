package com.dething.telbot.newBase.bot;

import com.dething.cloud.common.core.util.FreeMarkerUtil;
import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.base.entity.Bot;
import com.dething.telbot.base.entity.Duty;
import com.dething.telbot.newBase.events.DayTimeKeeper;
import com.dething.telbot.newBase.helper.UserHelper;
import com.dething.telbot.newBase.service.impl.BotService;
import com.dething.telbot.newBase.service.impl.DutyService;
import com.dething.telbot.newBase.enums.BotStatusEnum;
import com.dething.telbot.newBase.manager.BotGroupManager;
import com.dething.telbot.newBase.manager.BotCommandManager;
import com.dething.telbot.newBase.manager.BotUserManager;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.*;

public class TelBot extends TelegramLongPollingBot implements ITelBot, Observer {
    private BotStatusEnum status = BotStatusEnum.STOP;

    private final Bot bot;

    private Duty duty;

    public TelBot(Bot bot, DefaultBotOptions botOptions) {
        super(botOptions, bot.getToken());
        this.bot = bot;
        DayTimeKeeper timeKeeper = SpringUtil.getBean(DayTimeKeeper.class);
        timeKeeper.addObserver(this);
    }

    @Override
    public Bot getData() {
        return bot;
    }

    @Override
    public int getId() {
        return this.bot.getId();
    }

    @Override
    public Map<String, Object> getConfig () {
        Map<String, Object> config = this.bot.getConfig();
        if (config == null) {
            config = new HashMap<>();
            this.bot.setConfig(config);
        }
        return config;
    }

    @Override
    public boolean saveConfig () {
        BotService botService = SpringUtil.getBean(BotService.class);
        return botService.saveOrUpdate(this.bot);
    }

    @Override
    public boolean isPrivate() {
        return this.bot.getIsPrivate() == 1 && this.bot.getOwner() != null;
    }

    @Override
    public String getOwner() {
        return this.bot.getOwner();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (status == BotStatusEnum.STOP) return;

        try{
            BotCommandManager botCommandManager = SpringUtil.getBean(BotCommandManager.class);
            Message msg = update.getMessage();
            CallbackQuery callbackQuery = update.getCallbackQuery();

            long chatId = msg != null ? msg.getChatId() : callbackQuery.getMessage().getChatId();

            // 群组
            if (chatId < 0){
                BotGroupManager botGroupManager = SpringUtil.getBean(BotGroupManager.class);
                ITelGroup group = botGroupManager.getGroup(chatId);

                // 有message，代表发送了文字，或者进行了某些操作
                if (msg != null) {
                    // 缓存没有该群，机器人被邀请进群
                    if (group == null) {
                        List<User> userList = msg.getNewChatMembers();
                        if (userList == null) return;
                        boolean isJoinGroup = false;
                        for (User user: userList) {
                            if (!user.getUserName().equals(bot.getAccount())) continue;
                            isJoinGroup = true;
                            break;
                        }
                        if (!isJoinGroup) return;
                        botGroupManager.joinGroup(chatId, this.bot.getId(), msg.getFrom().getUserName());
                        String sendText = String.format("%s正在为您服务",this.getDuty().getName());
                        this.sendTextMessage(chatId, sendText);
                        return;
                    }
                    // 机器人被踢出群组
                    User leftUser = msg.getLeftChatMember();
                    if (leftUser != null && leftUser.getUserName().equals(bot.getAccount())) {
                        botGroupManager.exitGroup(chatId);
                        return;
                    }
                }
                botCommandManager.parseUpdate("group", update, this.bot.getId());
            } else if (chatId > 0) {
                this.updateUserChatId(update, chatId);
                botCommandManager.parseUpdate("private", update, this.bot.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUserChatId (Update update, Long chatId) {
        Message msg = update.getMessage();
        CallbackQuery callbackQuery = update.getCallbackQuery();

        if (msg == null && update.hasCallbackQuery()) {
            msg = callbackQuery.getMessage();
        }
        if (msg == null) return;
        BotUserManager botUserManager = SpringUtil.getBean(BotUserManager.class);
        String userName = msg.getFrom().getUserName();
        botUserManager.updateUserChatId(this.bot.getId(), userName, chatId);
    }

    @Override
    public void run() {
        try{
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
            this.status = BotStatusEnum.RUNNING;
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        this.status = BotStatusEnum.STOP;
    }


    @Override
    public Duty getDuty() {
        if (this.duty == null) {
            DutyService dutyService = SpringUtil.getBean(DutyService.class);
            this.duty = dutyService.getById(this.bot.getType());
        }
        return this.duty;
    }


    @Override
    public void sendMessage (SendMessage message) {
        try {
            this.execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendTextMessage(Long chatId, String text) {
        SendMessage message = SendMessage.builder().text(text).chatId(chatId).build();
        this.sendMessage(message);
    }

    @Override
    public void sendTextMessage(Long chatId, String text, ReplyKeyboard replyMarkup) {
        SendMessage message = SendMessage.builder().text(text).chatId(chatId).replyMarkup(replyMarkup).build();
        this.sendMessage(message);
    }

    @Override
    public void sendTemplateMessage (Long chatId, String templatePath, Map<String, Object> params) {
        try {
            String text = FreeMarkerUtil.getTemplateString(templatePath, params);
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(text);
            message.enableHtml(true);
            this.sendMessage(message);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return this.bot.getOwner();
    }

    @Override
    public void onCheckUsersExpireDaily (){
        BotUserManager botUserManager = SpringUtil.getBean(BotUserManager.class);
        List<com.dething.telbot.base.entity.User> userList = botUserManager.getUsersForList(this.bot.getId());
        if (userList.isEmpty()) return;

        LocalDateTime currentDateTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);

        for (com.dething.telbot.base.entity.User user: userList) {
            Duration duration = Duration.between(currentDateTime, user.getExpireTime());
            long diffDays = Math.abs(duration.toDays()) + (duration.isNegative() ? 1 : 0);
            if (diffDays > 3) continue;

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("absduibi", diffDays);

            if (duration.isNegative()) {
                this.sendTemplateMessage(user.getChatId(), "/user/expireOut.ftl", paramMap);
                continue;
            }
            if (duration.toDays() == 0) {
                this.sendTemplateMessage(user.getChatId(), "/user/expireToday.ftl",null);
            } else {
                this.sendTemplateMessage(user.getChatId(), "/user/expireCome.ftl",paramMap);
            }
        }
    }

    @Override
    public void onUserExpired() {

    }


    @Override
    public void update(Observable o, Object arg) {
        this.onCheckUsersExpireDaily();
    }
}
