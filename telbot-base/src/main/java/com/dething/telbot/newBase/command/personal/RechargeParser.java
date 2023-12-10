package com.dething.telbot.newBase.command.personal;

import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.base.common.IMessageHandler;
import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.base.common.impl.BasePrivateHandler;
import com.dething.telbot.base.entity.IRechargeClient;
import com.dething.telbot.base.entity.User;
import com.dething.telbot.newBase.command.BotCommandContext;
import com.dething.telbot.newBase.command.IBotCommandParser;
import org.telegram.telegrambots.meta.api.objects.Update;

public class RechargeParser implements IBotCommandParser {
    IRechargeClient rechargeClient;

    public RechargeParser(){
        this.rechargeClient = SpringUtil.getBean(IRechargeClient.class);
    }

    @Override
    public boolean parse(BotCommandContext context) {
        return context.getText().equals("充值");
    }

    @Override
    public void run(BotCommandContext context) {
        User user = context.getOperatorUser();
        String rechargeAddress = user.getRechargeAddress();
        if(rechargeAddress == null){
            rechargeAddress = this.rechargeClient.getAddress(user.getId(),"trc20");
            if(rechargeAddress == null){
                context.sendTextMessage( "充值通道繁忙，请稍后再试。");
                return;
            }
        }
        context.sendTextMessage("您的充值地址为："+ rechargeAddress);
    }
}
