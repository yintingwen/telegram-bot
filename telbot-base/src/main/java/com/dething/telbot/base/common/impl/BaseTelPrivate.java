package com.dething.telbot.base.common.impl;

import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.base.common.ITelPrivate;

/**
 * 机器人私聊
 */
public abstract class BaseTelPrivate extends HandlerList implements ITelPrivate {
    protected ITelBot telBot;

    /**
     * 构造函数
     * @param telBot ITelBot对象
     */
    public BaseTelPrivate(ITelBot telBot){
        this.telBot = telBot;
        initHandlerList();
    }

    /**
     * 初始化handler列表
     */
    abstract protected void initHandlerList();
}
