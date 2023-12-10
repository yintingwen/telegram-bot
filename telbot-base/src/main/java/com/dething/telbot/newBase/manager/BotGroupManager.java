package com.dething.telbot.newBase.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.base.entity.Group;
import com.dething.telbot.newBase.service.impl.GroupService;
import com.dething.telbot.newBase.bot.ITelGroup;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BotGroupManager implements ApplicationRunner {
    private final Map<Integer, Map<Long, ITelGroup>> botIdMap = new HashMap<>();

    private final Map<Long, ITelGroup> chatIdMap = new HashMap<>();

    protected abstract ITelGroup createGroup(Group group);

    abstract public void loadCommandParser (BotCommandManager commandManager);

    public ITelGroup getGroup (Long chatId) {
        return this.chatIdMap.get(chatId);
    }

    /**
     * 添加有一个群聊
     * @param group 群原始数据
     */
    public void addGroup (Group group) {
        GroupService groupService = SpringUtil.getBean(GroupService.class);
        groupService.saveOrUpdate(group);
        ITelGroup telGroup = this.createGroup(group);
        this.chatIdMap.put(group.getChatId(), telGroup);
        this.botIdMap.computeIfAbsent(group.getId(), k -> new HashMap<>()).put(group.getChatId(), telGroup);
    }

    /**
     * 加入一个群聊
     * @param chatId 群聊id
     * @param botId 机器人id
     * @param groupOwner 群聊拥有者
     */
    public void joinGroup (Long chatId, int botId, String groupOwner) {
        GroupService groupService = SpringUtil.getBean(GroupService.class);

        QueryWrapper<Group> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bot_id", botId);
        queryWrapper.eq("chat_id", chatId);
        Group group = groupService.getOne(queryWrapper);

        if (group == null) {
            group = new Group();
            group.setChatId(chatId);
            group.setBotId(botId);
            group.setOwner(groupOwner);
            group.setConfig(new HashMap<>());
        }
        group.setOffline(0);
        this.addGroup(group);
    }

    /**
     * 被踢出群
     * @param chatId 群聊id
     */
    public void exitGroup (Long chatId) {
        ITelGroup telGroup = this.chatIdMap.get(chatId);
        if (telGroup == null) return;
        telGroup.offline();
        this.chatIdMap.remove(chatId);
        this.botIdMap.get(telGroup.getData().getId()).remove(chatId);
    }

    public void loadData () {
        GroupService groupService = SpringUtil.getBean(GroupService.class);
        List<Group> groupList = groupService.list();
        groupList.forEach((group) -> {
            ITelGroup telGroup = this.createGroup(group);
            this.chatIdMap.put(group.getChatId(), telGroup);
            Map<Long, ITelGroup> groupMap = this.botIdMap.computeIfAbsent(group.getId(), k -> new HashMap<>());
            groupMap.put(group.getChatId(), telGroup);
        });
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        BotCommandManager botCommandManager = SpringUtil.getBean(BotCommandManager.class);
        this.loadData();
        this.loadCommandParser(botCommandManager);
    }
}
