package com.dething.telbot.base.user;

import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.base.entity.IRechargeClient;
import com.dething.telbot.base.common.IMessageHandler;
import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.base.common.impl.BasePrivateHandler;
import com.dething.telbot.base.entity.User;
import org.telegram.telegrambots.meta.api.objects.Update;

public class RechargeHandlerImpl extends BasePrivateHandler implements IMessageHandler {

    private long chatId;
    private String userName;

    public RechargeHandlerImpl(ITelBot telBot){
        super(telBot);
    }

    @Override
    public boolean parse(Update update) {
        if(!update.hasMessage() || !update.getMessage().hasText()) return false;
        String text = update.getMessage().getText().trim();

        if(text.equals("充值")){
            this.userName = update.getMessage().getFrom().getUserName();
            return true;
        }

        return false;
    }

    @Override
    public void execute() {
        User user = telBot.getUser(userName);
        String rechargeAddress = user.getRechargeAddress();
        if(rechargeAddress == null){
            IRechargeClient rechargeClient = SpringUtil.getBean(IRechargeClient.class);
            String address = rechargeClient.getAddress(user.getId(),"trc20");
            if(address == null){
                this.notifyMessage(user.getChatId(), "充值通道繁忙，请稍后再试。");
            }
            rechargeAddress = address;
        }
        this.notifyMessage(user.getChatId(), "您的充值地址为："+rechargeAddress);
    }
}
