package com.dething.telbot.newBase.command.personal;

import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.base.entity.User;
import com.dething.telbot.newBase.command.BotCommandContext;
import com.dething.telbot.newBase.command.IBotCommandParser;
import com.dething.telbot.newBase.command.IBotCommandParserWithId;
import com.dething.telbot.newBase.helper.UserHelper;
import com.dething.telbot.newBase.manager.BotUserManager;
import freemarker.template.SimpleDate;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
public class TrialParser implements IBotCommandParserWithId {
    String id = "personal:text:trial";

    BotUserManager botUserManager;

    public TrialParser () {
        this.botUserManager = SpringUtil.getBean(BotUserManager.class);
    }

    @Override
    public boolean parse(BotCommandContext context) {
        return context.getText().equals("试用") || context.getText().equals("shiyong") || context.getText().equals("开通账户");
    }

    @Override
    public void run(BotCommandContext context) {
        // 无account
        if (context.getOperatorAccount() == null) {
            context.sendTextMessage("你的Telegram账号未设置用户名，请设置用户名后再申请开通");
            return;
        }
        User user = context.getOperatorUser();
        // 无账户，创建用户
        if (user == null) {
            user = this.botUserManager.createUser(context.getBot(), context.getOperatorAccount());
        }
        // 如果是私有机器人，使用拥有者的用户信息
        if (context.getBot().isPrivate()) {
            user = UserHelper.getBotOwnerUser(context.getBot());
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String expireTime = formatter.format(user.getExpireTime());
        context.sendTextMessage("您的账户已开通，账号到期时间是: " + expireTime);
    }
}
