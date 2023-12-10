package com.dething.telbot.adsbot.domain;

import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.base.common.ITelGroup;
import com.dething.telbot.base.common.impl.BaseTelBot;
import com.dething.telbot.base.entity.Bot;
import com.dething.telbot.base.entity.Group;
import com.dething.telbot.base.enums.GroupAdminEnum;
import com.dething.telbot.newBase.service.IGroupAdminService;
import com.dething.telbot.newBase.service.impl.UserService;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import java.util.Observer;

/**
 * 机器人
 */
public class AdsTelBot extends BaseTelBot implements ITelBot, Observer {
    public UserService userService;

    /**
     * 构造函数
     * @param bot 机器人数据
     * @param botOptions 配置,代理信息等
     */
    public AdsTelBot(Bot bot, DefaultBotOptions botOptions){
        super(bot, botOptions);

        userService = SpringUtil.getBean(UserService.class);
    }


    @Override
    protected void mountPrivateImpl(){
        this.telPrivate = new AdsPrivate(this);
    }

    @Override
    protected ITelGroup mountGroupImpl(Group group){
        // 生成TelGroup对象
        ITelGroup telGroup = new AdsGroup(group, this);
        this.groupMap.put(group.getChatId(), telGroup);

        return telGroup;
    }

    @Override
    protected void initConfig(Group group){
        // 创建权限人
        IGroupAdminService groupAdminService = SpringUtil.getBean(IGroupAdminService.class);
        groupAdminService.addAdmin(group.getId(), group.getOwner(), GroupAdminEnum.admin.getValue());
    }

}
