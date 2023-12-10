package com.dething.telbot.newBase.bot;

import com.dething.telbot.base.entity.Bot;
import com.dething.telbot.base.entity.Duty;
import com.dething.telbot.base.entity.User;

import java.util.Map;

public interface ITelBotData {
    /**
     * 获取原始数据
     */
    Bot getData();

    /**
     * 获取类型数据
     */
    Duty getDuty();

    /**
     * 获取机器人ID
     */
    int getId ();

    /**
     * 获取拥有者账户名
     */
    String getOwner ();

    /**
     * 是否是私有机器人
     */
    boolean isPrivate();

    /**
     * 获取配置
     */
    Map<String, Object> getConfig();

    /**
     * 保存配置
     */
    boolean saveConfig();
}
