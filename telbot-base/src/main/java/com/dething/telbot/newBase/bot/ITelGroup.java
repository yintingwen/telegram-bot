package com.dething.telbot.newBase.bot;

import com.dething.telbot.base.entity.Group;

import java.util.Map;

public interface ITelGroup {
    /**
     * 获取原始group数据
     */
    Group getData();

    /**
     * 获取群组配置
     */
    Map<String, Object> getConfig();

    /**
     * 保存配置
     */
    boolean saveConfig();

    /**
     * 运行
     */
    void online();

    /**
     * 下线
     */
    void offline();
}
