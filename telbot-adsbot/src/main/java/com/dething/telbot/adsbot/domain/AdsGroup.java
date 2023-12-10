package com.dething.telbot.adsbot.domain;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dething.cloud.common.core.util.DateUtil;
import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.adsbot.domain.handler.group.*;
import com.dething.telbot.adsbot.entity.BotAds;
import com.dething.telbot.adsbot.entity.BotMedia;
import com.dething.telbot.adsbot.service.IBotAdsService;
import com.dething.telbot.adsbot.service.IBotMediaService;
import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.base.common.ITelGroup;
import com.dething.telbot.base.common.impl.BaseTelGroup;
import com.dething.telbot.base.entity.Group;
import com.dething.telbot.newBase.events.MinuteTimeKeeper;
import com.dething.telbot.newBase.service.IGroupService;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.ParseException;
import java.util.*;

public class AdsGroup extends BaseTelGroup<Object> implements ITelGroup<Object>, Observer {

    private List<BotMedia> mediaList = new ArrayList<>();

    IGroupService groupService;

    IBotAdsService botAdsService;

    /**
     * 构造函数
     */
    public AdsGroup(Group group, ITelBot telBot){
        super(group, telBot);

        groupService = SpringUtil.getBean(IGroupService.class);
        botAdsService = SpringUtil.getBean(IBotAdsService.class);

        MinuteTimeKeeper timeKeeper = SpringUtil.getBean(MinuteTimeKeeper.class);
        timeKeeper.addObserver(this);
    }


    @Override
    public void onMessage(Update update){
        // 处理群消息
        if(update.getMessage()!=null){
            // 仅处理来自管理员的消息
            this.handleMessage(update);
        }
    }

    @Override
    public void restart(String startTime){
        this.group.getConfig().put("start", startTime);
        IGroupService groupService = SpringUtil.getBean(IGroupService.class);
        groupService.saveOrUpdate(this.group);
        this.initTaskData();
    }


    /**
     * 初始化消息处理对象列表
     */
    protected void initHandlerList(){
        this.addMessageHandler(new StartHandler(this));
        this.addMessageHandler(new StopHandler(this));
        this.addMessageHandler(new SetIntervalHandler(this));
        this.addMessageHandler(new AdsAddHandler(this));
        this.addMessageHandler(new SetColumnsHandler(this));
        this.addMessageHandler(new AdsAddHandler(this));
        this.addMessageHandler(new TestHandler(this));
    }

    @Override
    protected void initTaskData() {

    }

    @Override
    public void update(Observable o, Object arg) {
        // 判断间隔时间是否正确
        try {
            this.handleAds();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testAds() throws TelegramApiException {
        // 处理轮播
        if(this.mediaList.size()==0){
            // 加载数据
            this.loadMedias();
        }

        if(this.mediaList.size()>0){
            BotMedia botMedia = this.mediaList.remove(0);
            // 发送
            this.sendMedia(botMedia);
        }
    }

    protected void handleAds() throws ParseException, TelegramApiException {
        boolean bStart = true;
        Object startObject = this.group.getConfig().get("start");
        if(startObject!=null){
            bStart = Boolean.parseBoolean(startObject.toString());
        }
        if(!bStart) return;

        IGroupService groupService = SpringUtil.getBean(IGroupService.class);

        // 默认10分钟播放一次
        int interval = 10;
        // 获取间隔配置
        Object intervalObject = this.group.getConfig().get("interval");
        if(intervalObject!=null){
            interval = Integer.parseInt(intervalObject.toString());
        }

        Date last = new Date(0);
        Object lastObject = this.group.getConfig().get("last");
        if(lastObject!=null){
            last = DateUtil.parse2Date(lastObject.toString(),DateUtil.YYYY_MM_DD_HH_MM_SS);
        }

        Date now = new Date();
        long diff = now.getTime() - last.getTime();
        if(diff >= interval*60*1000){
            this.group.getConfig().put("last", DateUtil.format(now, DateUtil.YYYY_MM_DD_HH_MM_SS));
            groupService.saveOrUpdate(this.group);

            // 处理轮播
            if(this.mediaList.size()==0){
                // 加载数据
                this.loadMedias();
            }

            if(this.mediaList.size()>0){
                BotMedia botMedia = this.mediaList.remove(0);
                // 发送
                this.sendMedia(botMedia);
            }
        }
    }

    protected void loadMedias(){
        int cursor = 0;
        Object cursorObject = this.group.getConfig().get("cursor");
        if(cursorObject!=null){
            cursor = Integer.parseInt(cursorObject.toString());
        }

        // 加载数据
        IBotMediaService mediaService = SpringUtil.getBean(IBotMediaService.class);
        QueryWrapper<BotMedia> queryWrapper = new QueryWrapper();
        queryWrapper.eq("bot_id", telBot.getId()).and( qw -> {
            qw.eq("user_id", this.getOwnerUser().getId());
        }).gt("id", cursor);
        queryWrapper.last("LIMIT 10");
        this.mediaList = mediaService.list(queryWrapper);
        if(mediaList.size()>0){
            BotMedia last = mediaList.get(mediaList.size()-1);
            this.group.getConfig().put("cursor", last.getId());
            groupService.saveOrUpdate(this.group);
        }else{
            // 查找不到数据，重置为0
            this.group.getConfig().put("cursor", 0);
            groupService.saveOrUpdate(this.group);
        }
    }

    protected void sendMedia(BotMedia botMedia) throws TelegramApiException {
        // 创建caption
        String captions = this.generateCaption();
        InlineKeyboardMarkup replyMarkup = this.createInlineKeyboard();
        if(botMedia.getMediaType().equals("video")){
            SendVideo sendVideo = new SendVideo();
            sendVideo.setChatId(this.group.getChatId());
            sendVideo.setVideo(new InputFile(botMedia.getFileId()));
            sendVideo.setCaption(captions);
            sendVideo.setParseMode("HTML");
            if(replyMarkup!=null) sendVideo.setReplyMarkup(replyMarkup);
            this.absSender.execute(sendVideo);
        }else if(botMedia.getMediaType().equals("image")){
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(this.group.getChatId());
            sendPhoto.setPhoto(new InputFile(botMedia.getFileId()));
            sendPhoto.setCaption(captions);
            sendPhoto.setParseMode("HTML");
            if(replyMarkup!=null) sendPhoto.setReplyMarkup(replyMarkup);
            this.absSender.execute(sendPhoto);
        }else if(botMedia.getMediaType().equals("animation")){
            SendAnimation sendAnimation = new SendAnimation();
            sendAnimation.setChatId(this.group.getChatId());
            sendAnimation.setAnimation(new InputFile(botMedia.getFileId()));
            sendAnimation.setCaption(captions);
            sendAnimation.setParseMode("HTML");
            if(replyMarkup!=null) sendAnimation.setReplyMarkup(replyMarkup);
            this.absSender.execute(sendAnimation);
        }

    }

    protected String generateCaption(){
        // 从数据库读取CaptionItems
        QueryWrapper<BotAds> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bot_id", telBot.getId());
        queryWrapper.and(qw -> {
            qw.eq("user_id", 0).or().eq("user_id",getOwnerUser().getId()).eq("group_id",0);
        });
        queryWrapper.orderBy(true,true,"user_id");
        List<BotAds> adsList = botAdsService.list(queryWrapper);
        String captions = "";

        for(int i=0; i<adsList.size(); i++){
            BotAds item = adsList.get(i);
            String link = item.getLink();
            if(!link.startsWith("http")){
                if(link.startsWith("@")){
                    link = link.substring(1);
                }
                link = String.format("https://t.me/%s",link);
            }
            captions += String.format("<a href=\"%s\">%s</a>\n",link,item.getText());
        }

        return captions;
    }

    private InlineKeyboardMarkup createInlineKeyboard() {
        QueryWrapper<BotAds> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bot_id", telBot.getId());
        queryWrapper.eq("user_id", getOwnerUser().getId());
        queryWrapper.eq("group_id", group.getId());

        List<BotAds> adsList = botAdsService.list(queryWrapper);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        int columns = 1;
        Object columnsObject = this.group.getConfig().get("columns");
        if(columnsObject!=null){
            columns = Integer.parseInt(columnsObject.toString());
        }

        for(int i=0; i<adsList.size(); i++){
            BotAds item = adsList.get(i);

            if(i%columns==0){
                row = new ArrayList<>();
            }

            String link = item.getLink();
            if(!link.startsWith("http")){
                if(link.startsWith("@")){
                    link = link.substring(1);
                }
                link = String.format("https://t.me/%s",link);
            }

            InlineKeyboardButton button = InlineKeyboardButton.builder().text(item.getText()).url(link).build();
            row.add(button);

            // 下一个元素换行或者为最后一个元素
            if((i+1)%columns==0 || i==adsList.size()-1){
                rows.add(row);
            }
        }
        InlineKeyboardMarkup markup = null;
        if(rows.size()!=0){
            markup = new InlineKeyboardMarkup();
            markup.setKeyboard(rows);
        }

        return markup;
    }

}
