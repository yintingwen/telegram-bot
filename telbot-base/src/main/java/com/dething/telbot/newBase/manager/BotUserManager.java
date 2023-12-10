package com.dething.telbot.newBase.manager;

import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.base.entity.User;
import com.dething.telbot.newBase.bot.ITelBot;
import com.dething.telbot.newBase.service.impl.UserService;
import com.dething.telbot.newBase.bot.TelBot;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class BotUserManager implements ApplicationRunner {
    UserService userService;

    final Map<Integer, Map<String, User>> botIdMap = new HashMap<>();

    public User getUser (int botId, String account) {
        Map<String, User> userMap = this.botIdMap.get(botId);
        if (userMap == null) return null;
        return userMap.get(account);
    }

    public User getUser (TelBot telBot, String account) {
        int botId = telBot.getData().getId();
        return this.getUser(botId, account);
    }

    public List<User> getUsersForList (int botId) {
        return this.botIdMap.get(botId) == null ? new ArrayList<>() : new ArrayList<>(this.botIdMap.get(botId).values());
    }

    public void addUser (User user) {
        // 保存到数据库
        this.userService.saveOrUpdate(user);
        // 保存到内存
        Map<String, User> accountMap = this.botIdMap.computeIfAbsent(user.getId(), k -> new HashMap<>());
        this.botIdMap.put(user.getId(), accountMap);
    }

    public User createUser (ITelBot telbot, String userName) {
        User user = this.getUser(telbot.getData().getId(), userName);
        if (user == null) {
            user = new User();
            user.setBotId(telbot.getData().getId());
            user.setAccount(userName);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, telbot.getDuty().getTrailPeriod());
            user.setExpireTime(LocalDateTime.now().plusHours(telbot.getDuty().getTrailPeriod()));
            this.addUser(user);
        }
        return user;
    }

    /**
     * 更新用戶的聊天ID
     * @param botId 机器人ID
     * @param account 用戶账号
     * @param chatId 用戶聊天ID
     */
    public void updateUserChatId (int botId, String account, Long chatId) {
        User user = this.getUser(botId, account);
        if (user == null) return;
        if (user.getChatId() != null && user.getChatId().equals(chatId)) return;
        user.setChatId(chatId);
        this.userService.saveOrUpdate(user);
    }

    public void load () {
        this.userService = SpringUtil.getBean(UserService.class);
        this.userService.list().forEach((user) -> {
            Map<String, User> userMap = botIdMap.computeIfAbsent(user.getId(), k -> new HashMap<>());
            userMap.put(user.getAccount(), user);
        });
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.load();
    }
}
