package com.dething.telbot.adsbot.parser.personal;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.adsbot.domain.AdsPrivateHandler;
import com.dething.telbot.adsbot.entity.BotAds;
import com.dething.telbot.adsbot.service.IBotAdsService;
import com.dething.telbot.base.common.IMessageHandler;
import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.base.entity.Bot;
import com.dething.telbot.base.entity.User;
import com.dething.telbot.newBase.command.BotCommandContext;
import com.dething.telbot.newBase.command.IBotCommandParser;
import com.dething.telbot.newBase.manager.BotUserManager;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdsAddParser implements IBotCommandParser {
    BotUserManager botUserManager;

    IBotAdsService botAdsService;

    public AdsAddParser () {
        this.botUserManager = SpringUtil.getBean(BotUserManager.class);
        this.botAdsService = SpringUtil.getBean(IBotAdsService.class);
    };

    private final String command = "ad(\\d+)\\s*\\|\\s*(\\S+)\\s*\\|\\s*(\\S+)";

    @Override
    public boolean parse(BotCommandContext context) {
        return Pattern.matches(this.command, context.getText());
    }

    @Override
    public void run(BotCommandContext context) {
        User user = context.getOperatorUser();
        Matcher m = Pattern.compile(this.command).matcher(context.getText());
        QueryWrapper<BotAds> queryWrapper = new QueryWrapper<BotAds>()
                .eq("bot_id", context.getBotId())
                .eq("user_id", user.getId())
                .eq("group_id", 0)
                .eq("tag", m.group(1));
        BotAds botAds = botAdsService.getOne(queryWrapper);

        if(botAds==null){
            botAds = new BotAds();
            botAds.setBotId(context.getBotId());
            botAds.setUserId(user.getId());
            botAds.setGroupId(0);
            botAds.setTag(m.group(1));
        }

        botAds.setText(m.group(2));
        botAds.setLink(m.group(3));
        botAdsService.saveOrUpdate(botAds);

        context.sendTextMessage(m.group(1) + " 设置成功!");
    }
}
