package com.dething.telbot.adsbot.domain.handler.group;

import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.adsbot.domain.AdsGroupHandler;
import com.dething.telbot.base.common.IMessageHandler;
import com.dething.telbot.base.common.ITelGroup;
import com.dething.telbot.newBase.service.IGroupService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StopHandler extends AdsGroupHandler implements IMessageHandler {

    private String operator;


    public StopHandler(ITelGroup telGroup){
        super(telGroup);
    }

    @Override
    public boolean parse(Update update){
        String text = update.getMessage().getText();

        if(!text.equals("/zt")){
            return false;
        }

        this.operator = update.getMessage().getFrom().getUserName();

        return true;
    }

    @Override
    public void execute() {
        if (!this.checkPermission(this.operator)) return;

        //设置未启动状态
        telGroup.getGroup().getConfig().put("start", false);
        IGroupService groupService = SpringUtil.getBean(IGroupService.class);
        groupService.saveOrUpdate(telGroup.getGroup());

        telGroup.notifyMessage("停止运行");
    }
}
