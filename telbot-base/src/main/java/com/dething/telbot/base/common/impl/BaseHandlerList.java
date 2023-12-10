package com.dething.telbot.base.common.impl;

import com.dething.telbot.base.common.IHandlerList;
import com.dething.telbot.base.common.IMessageHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

public class BaseHandlerList implements IHandlerList {
    protected List<IMessageHandler> handlerList = new ArrayList<>();

    /**
     * 消息处理列表
     * @param update
     */
    public void handleMessage(Update update){
        for(int i=0; i<handlerList.size(); i++){

            boolean bParseSuccess = false;

            try {
                IMessageHandler handler = handlerList.get(i);
                if(handler.parse(update)){
                    bParseSuccess = true;
                    //解析成功
                    handler.execute();
                    break;
                }
            }
            catch (Exception e){
                e.printStackTrace();
                if(bParseSuccess) break;
            }

        }
    }

    /**
     * 增加一个指令处理对象
     * @param handler
     */
    public void addMessageHandler(IMessageHandler handler){
        handlerList.add(handler);
    }
}
