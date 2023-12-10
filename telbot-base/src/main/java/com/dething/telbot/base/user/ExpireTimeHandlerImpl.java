package com.dething.telbot.base.user;

import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.base.common.IMessageHandler;
import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.base.common.impl.BasePrivateHandler;
import com.dething.telbot.base.entity.User;
import com.dething.telbot.newBase.service.IUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.text.SimpleDateFormat;

public class ExpireTimeHandlerImpl extends BasePrivateHandler implements IMessageHandler {

    private String userName;
    private long chatId;

    public IUserService userService;

    public ExpireTimeHandlerImpl(ITelBot telBot){
        super(telBot);
        userService = SpringUtil.getBean(IUserService.class);
    }

    @Override
    public boolean parse(Update update) {
        if(update.getMessage()==null) return false;

        String text = update.getMessage().getText();
        if(text!=null && text.equals("到期时间")){
            this.chatId = update.getMessage().getChatId();
            this.userName = update.getMessage().getFrom().getUserName();

            return true;
        }

        return false;
    }

    @Override
    public void execute() {
        User user = this.telBot.getUser(userName);
        if(user == null){
            //用户不存在
            this.notifyMessage(chatId, "您还没有申请试用");
        }else{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String expireTime = formatter.format(user.getExpireTime());
            this.notifyMessage(chatId, "您的账号到期时间是: " + expireTime);
        }
    }

}
