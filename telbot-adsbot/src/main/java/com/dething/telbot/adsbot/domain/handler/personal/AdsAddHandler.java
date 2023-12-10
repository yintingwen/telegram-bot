package com.dething.telbot.adsbot.domain.handler.personal;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.adsbot.domain.AdsPrivateHandler;
import com.dething.telbot.adsbot.entity.BotAds;
import com.dething.telbot.adsbot.service.IBotAdsService;
import com.dething.telbot.base.common.IMessageHandler;
import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.base.entity.User;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AdsAddHandler extends AdsPrivateHandler implements IMessageHandler {
    public AdsAddHandler(ITelBot telBot) {
        super(telBot);
    }

    private String sender;
    private long chatId;

    private String tag;
    private String text;

    private String link;

    @Override
    public boolean parse(Update update) {
        String command = "ad";

        if(update.getMessage()==null) return false;

        String msg = update.getMessage().getText();

        if(!msg.startsWith(command))  return false;

        String[] columns = msg.split("\\|");
        if(columns.length<3) return false;

        String firstColumn = columns[0];
        String lastColumn = columns[columns.length - 1];

        StringBuilder middleColumn = new StringBuilder();
        for (int i = 1; i < columns.length - 1; i++) {
            middleColumn.append(columns[i]);
            if (i < columns.length - 2) {
                middleColumn.append("|");
            }
        }

        this.tag = firstColumn.trim();
        this.text = middleColumn.toString();
        this.link = lastColumn.trim();

        this.sender = update.getMessage().getFrom().getUserName();
        this.chatId = update.getMessage().getChatId();

        return true;
    }

    @Override
    public void execute() {
        User user = telBot.getUser(this.sender);
        if(user==null){
            notifyMessage(this.chatId, "未创建用户，请点击试用申请账户");
            return;
        }
        IBotAdsService botAdsService = SpringUtil.getBean(IBotAdsService.class);
        QueryWrapper<BotAds> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bot_id", telBot.getId()).and(qw->{
            qw.eq("user_id", user.getId());
        }).and(qw->{
            qw.eq("group_id", 0);   //  账户设置的ADS
        }).and(qw->{
            qw.eq("tag", this.tag);
        });
        BotAds botAds = botAdsService.getOne(queryWrapper);
        if(botAds==null){
            botAds = new BotAds();
            botAds.setBotId(telBot.getId());
            botAds.setUserId(user.getId());
            botAds.setGroupId(0);
            botAds.setTag(this.tag);
        }

        botAds.setText(this.text);
        botAds.setLink(this.link);
        botAdsService.saveOrUpdate(botAds);

        this.notifyMessage(this.chatId, this.tag + " 设置成功!");
    }
}
