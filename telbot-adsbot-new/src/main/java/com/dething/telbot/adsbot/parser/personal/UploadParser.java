package com.dething.telbot.adsbot.parser.personal;

import com.dething.telbot.adsbot.domain.AdsPrivateHandler;
import com.dething.telbot.adsbot.entity.BotMedia;
import com.dething.telbot.base.common.IMessageHandler;
import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.base.entity.User;
import com.dething.telbot.newBase.command.BotCommandContext;
import com.dething.telbot.newBase.command.IBotCommandParser;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UploadParser implements IBotCommandParser {
    @Override
    public boolean parse(BotCommandContext context) {
        String mediaType = context.getMediaType();
        return mediaType.equals("video") || mediaType.equals("image") || mediaType.equals("animation");
    }

    @Override
    public void run(BotCommandContext context) {
//        if(!this.checkUser(chatId,sender)){
//            return;
//        }

//        User user = this.telBot.getUser(sender);
//
//        BotMedia botMedia = new BotMedia();
//        botMedia.setBotId(this.telBot.getId());
//        botMedia.setUserId(user.getId());
//        botMedia.setMediaType(mediaType);
//        botMedia.setFileId(this.fileId);
//
//        this.botMediaService.save(botMedia);

        context.sendTextMessage("上传成功");
    }
}
