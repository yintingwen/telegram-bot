package com.dething.telbot.base.user;

import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.base.common.IMessageHandler;
import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.base.common.impl.BasePrivateHandler;
import com.dething.telbot.base.entity.User;
import com.dething.telbot.newBase.service.IUserService;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TrialHandlerImpl extends BasePrivateHandler implements IMessageHandler {

    private String userName;
    private long chatId;

    public IUserService userService;

    public TrialHandlerImpl(ITelBot telBot){
        super(telBot);
        userService = SpringUtil.getBean(IUserService.class);
    }

    @Override
    public boolean parse(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        if(callbackQuery!=null){
            Message msg = callbackQuery.getMessage();
            if(msg != null){
                String textMsg = msg.getText();
                if(Objects.equals(textMsg, "shiyong")){
                    this.chatId = callbackQuery.getMessage().getChatId();
                    this.userName = callbackQuery.getFrom().getUserName();
                    return true;
                }
            }
        }

        String text = update.getMessage().getText();
        if(text!=null && text.equals("试用")){
            this.chatId = update.getMessage().getChatId();
            this.userName = update.getMessage().getFrom().getUserName();

            return true;
        }
        return false;
    }

    @Override
    public void execute() {
        if (userName == null) {
            this.notifyMessage(chatId, "你的Telegram账号未设置用户名，请设置用户名后再申请试用");
        } else {
            this.trial(userName, chatId);
        }
    }

    private void trial(String userName, long chatId) {
        User user = telBot.getUser(userName);
        if (user == null) {
            // 触发新用户的事件
            user = this.telBot.createUser(userName);
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("expireTime", user.getExpireTime());

            this.templateMessage(chatId, "/cashier/start.ftl", paramMap);
        }else{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String expireTime = formatter.format(user.getExpireTime());
            this.notifyMessage(chatId, "您的账户已开通，账号到期时间是: " + expireTime);
        }
    }
}
