package com.dething.telbot.adsbot.domain.handler.personal;

import com.dething.telbot.adsbot.domain.AdsPrivateHandler;
import com.dething.telbot.adsbot.entity.BotMedia;
import com.dething.telbot.base.common.IMessageHandler;
import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.base.entity.User;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UploadHandler extends AdsPrivateHandler implements IMessageHandler {
    public UploadHandler(ITelBot telBot) {
        super(telBot);
    }

    private String fileId;
    private String mediaType;
    private String sender;

    private long chatId;

    @Override
    public boolean parse(Update update) {

        if (update.hasMessage() && update.getMessage().hasVideo()) {
            fileId = update.getMessage().getVideo().getFileId();
            mediaType = "video";
        }else if (update.hasMessage() && update.getMessage().hasPhoto()) {
            fileId = update.getMessage().getPhoto().get(0).getFileId();
            mediaType = "image";
        }else if (update.hasMessage() && update.getMessage().hasAnimation()) {
            fileId = update.getMessage().getAnimation().getFileId();
            mediaType = "animation";
        }else{
            // 不支持的格式
            return false;
        }

        // 获取发送者账号
        sender = update.getMessage().getFrom().getUserName();
        chatId = update.getMessage().getChatId();

        return true;
    }

    @Override
    public void execute() {
        // 通过账号找到用户
        if(!this.checkUser(chatId,sender)){
            return;
        }

        User user = this.telBot.getUser(sender);

        BotMedia botMedia = new BotMedia();
        botMedia.setBotId(this.telBot.getId());
        botMedia.setUserId(user.getId());
        botMedia.setMediaType(mediaType);
        botMedia.setFileId(this.fileId);

        this.botMediaService.save(botMedia);

        this.notifyMessage(chatId,"上传成功");
    }
}
