package com.dething.telbot.newBase.command.interceptor;

import com.dething.telbot.base.entity.User;
import com.dething.telbot.newBase.bot.ITelBot;
import com.dething.telbot.newBase.command.BotCommandContext;
import com.dething.telbot.newBase.command.IBotCommandBeforeRunMultiple;
import com.dething.telbot.newBase.helper.UserHelper;
import com.dething.telbot.newBase.manager.BotUserManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CheckUserInterceptor implements IBotCommandBeforeRunMultiple {
    BotUserManager botUserManager;

    @Override
    public List<String> getIds() {
        return new ArrayList<String>(){{
            add("personal:text:expireTime"); // 到期时间
            add("personal:text:recharge"); // 充值
            add("personal:text:adsAdd"); // 添加广告
            add("personal:media:upload"); // 上传媒体文件
        }};
    }
    @Override
    public boolean run(BotCommandContext context) {
        ITelBot telBot = context.getBot();
        User accountUser = context.getOperatorUser();
        if (accountUser == null) {
            context.sendTextMessage("您还没有开通账号，请点击开通账户按钮");
            return false;
        }
        // 私有状态，试用拥有者的用户信息
        if (telBot.isPrivate()) {
            accountUser = UserHelper.getBotOwnerUser(telBot);
        }
        if (accountUser.getExpireTime().isBefore(LocalDateTime.now())) {
            context.sendTextMessage("您的账号已过期，请点击充值进行续费");
            return false;
        }
        return true;
    }
}
