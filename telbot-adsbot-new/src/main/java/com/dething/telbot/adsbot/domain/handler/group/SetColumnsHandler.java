package com.dething.telbot.adsbot.domain.handler.group;

import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.adsbot.domain.AdsGroupHandler;
import com.dething.telbot.base.common.IMessageHandler;
import com.dething.telbot.base.common.ITelGroup;
import com.dething.telbot.newBase.service.IGroupService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SetColumnsHandler extends AdsGroupHandler implements IMessageHandler {

    private String operator;

    private int columns = 1;

    public SetColumnsHandler(ITelGroup telGroup){
        super(telGroup);
    }

    @Override
    public boolean parse(Update update){
        this.columns = 1;

        String text = update.getMessage().getText();

        String command = "设置展示列数";

        if(!text.startsWith(command)){
            return false;
        }

        String columnsString = text.substring(command.length()).trim();
        try{
            this.columns = Integer.parseInt(columnsString);
        }
        catch (NumberFormatException e){
            e.printStackTrace();
            return false;
        }

        this.operator = update.getMessage().getFrom().getUserName();

        return true;
    }

    @Override
    public void execute() {
        if (!this.checkPermission(this.operator)) return;

        //设置未启动状态
        telGroup.getGroup().getConfig().put("columns", this.columns);
        IGroupService groupService = SpringUtil.getBean(IGroupService.class);
        groupService.saveOrUpdate(telGroup.getGroup());

        telGroup.notifyMessage("设置成功");
    }
}
