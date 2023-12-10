package com.dething.telbot.adsbot.parser.group;

import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.adsbot.domain.AdsGroupHandler;
import com.dething.telbot.base.common.IMessageHandler;
import com.dething.telbot.base.common.ITelGroup;
import com.dething.telbot.newBase.command.BotCommandContext;
import com.dething.telbot.newBase.command.IBotCommandParserWithId;
import com.dething.telbot.newBase.service.IGroupService;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public class StartHandler implements IBotCommandParserWithId {
    String id = "group:text:start";
    private String operator;

//    @Override
//    public void execute() {
//        if (!this.checkPermission(this.operator)) return;
//
//        //设置未启动状态
//        telGroup.getGroup().getConfig().put("start", true);
//        IGroupService groupService = SpringUtil.getBean(IGroupService.class);
//        groupService.saveOrUpdate(telGroup.getGroup());
//
//        telGroup.notifyMessage("启动成功");
//    }

    @Override
    public boolean parse(BotCommandContext context) {
        return context.getText().equals("kc") || context.getText().equals("启动");
    }

    @Override
    public void run(BotCommandContext context) {

    }
}
