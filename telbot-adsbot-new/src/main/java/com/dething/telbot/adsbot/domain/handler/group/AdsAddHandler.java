package com.dething.telbot.adsbot.domain.handler.group;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.adsbot.domain.AdsGroupHandler;
import com.dething.telbot.adsbot.entity.BotAds;
import com.dething.telbot.adsbot.service.IBotAdsService;
import com.dething.telbot.base.common.IMessageHandler;
import com.dething.telbot.base.common.ITelGroup;
import com.dething.telbot.base.entity.User;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AdsAddHandler extends AdsGroupHandler implements IMessageHandler {

    private String tag;

    private String text;

    private String link;

    private String sender;


    public AdsAddHandler(ITelGroup telGroup){
        super(telGroup);
    }

    @Override
    public boolean parse(Update update){
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

        return true;
    }

    @Override
    public void execute() {
        if (!this.checkPermission(this.operator)) return;

        User user = this.telGroup.getOwnerUser();
        if(user==null){
            return;
        }
        IBotAdsService botAdsService = SpringUtil.getBean(IBotAdsService.class);
        QueryWrapper<BotAds> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bot_id", telGroup.getId()).and(qw->{
            qw.eq("user_id", user.getId());
        }).and(qw->{
            qw.eq("group_id", telGroup.getGroup().getId());   //  群组设置的ADS
        }).and(qw->{
            qw.eq("tag", this.tag);
        });
        BotAds botAds = botAdsService.getOne(queryWrapper);
        if(botAds==null){
            botAds = new BotAds();
            botAds.setBotId(telGroup.getId());
            botAds.setUserId(user.getId());
            botAds.setGroupId(telGroup.getGroup().getId());
            botAds.setTag(this.tag);
        }

        botAds.setText(this.text);
        botAds.setLink(this.link);
        botAdsService.saveOrUpdate(botAds);

        telGroup.notifyMessage(this.tag + " 设置成功!");
    }
}
