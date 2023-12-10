package com.dething.telbot.newBase.command.personal;

import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.base.entity.User;
import com.dething.telbot.newBase.command.BotCommandContext;
import com.dething.telbot.newBase.command.IBotCommandParserWithId;
import com.dething.telbot.newBase.helper.UserHelper;
import com.dething.telbot.newBase.manager.BotUserManager;
import lombok.Getter;

import java.text.SimpleDateFormat;

@Getter
public class ExpireTimeParser implements IBotCommandParserWithId {
    String id = "personal:text:expireTime";

    private final BotUserManager botUserManager;

    public ExpireTimeParser() {
        this.botUserManager = SpringUtil.getBean(BotUserManager.class);
    }

    @Override
    public boolean parse(BotCommandContext context) {
        return context.getText().equals("到期时间");
    }

    @Override
    public void run(BotCommandContext context) {
        User ownerUser = UserHelper.getSessionOwnerUser(context.getBot(), context.getOperatorAccount());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String expireTime = formatter.format(ownerUser.getExpireTime());
        context.sendTextMessage("您的账号到期时间是: " + expireTime);
    }
}
