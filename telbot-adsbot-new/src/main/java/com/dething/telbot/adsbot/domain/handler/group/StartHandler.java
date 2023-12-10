package com.dething.telbot.adsbot.domain.handler.group;

import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.adsbot.domain.AdsGroupHandler;
import com.dething.telbot.base.common.IMessageHandler;
import com.dething.telbot.base.common.ITelGroup;
import com.dething.telbot.newBase.service.IGroupService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartHandler extends AdsGroupHandler implements IMessageHandler {

    private String operator;


    public StartHandler(ITelGroup telGroup){
        super(telGroup);
    }

    @Override
    public boolean parse(Update update){
        String text = update.getMessage().getText();

        String command = "/kc";

        if(!text.equals(command)){
            return false;
        }

        this.operator = update.getMessage().getFrom().getUserName();

        return true;
    }

    @Override
    public void execute() {
        if (!this.checkPermission(this.operator)) return;

        //设置未启动状态
        telGroup.getGroup().getConfig().put("start", true);
        IGroupService groupService = SpringUtil.getBean(IGroupService.class);
        groupService.saveOrUpdate(telGroup.getGroup());

        telGroup.notifyMessage("启动成功");
    }
}
