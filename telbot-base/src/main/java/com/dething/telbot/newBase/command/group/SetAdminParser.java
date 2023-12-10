package com.dething.telbot.newBase.command.group;

import com.dething.cloud.common.core.util.EnumUtil;
import com.dething.telbot.base.common.ITelGroup;
import com.dething.telbot.base.common.impl.BaseGroupHandler;
import com.dething.telbot.base.entity.GroupAdmin;
import com.dething.telbot.base.enums.GroupAdminEnum;
import com.dething.telbot.base.manager.BotGroupAdminManager;
import com.dething.telbot.newBase.enums.SetAdminCommandEnum;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetAdminParser {
//    private SetAdminCommandEnum commandType = SetAdminCommandEnum.unknown;
//
//    private final List<String> accountList = new ArrayList<>();
//
//    public SetAdminParser(ITelGroup<T> telGroup){
//        super(telGroup);
//    }
//
//    @Override
//    public boolean parse(Update update) {
//        if(update.getMessage()==null) return false;
//
//        this.commandType = SetAdminCommandEnum.unknown;
//        this.accountList.clear();
//
//        String text = update.getMessage().getText();
//        String[] splitCommand = text.split("@");
//
//        String command = splitCommand[0].trim();
//        String[] pendingAccountList = Arrays.copyOfRange(splitCommand,1,splitCommand.length);
//
//        try{
//            this.commandType = EnumUtil.fromPropertyValue(SetAdminCommandEnum.class, SetAdminCommand::getValue, command);
//        }catch (Exception e){
//            this.commandType = SetAdminCommandEnum.unknown;
//        }
//        // 未知命令
//        if(this.commandType == SetAdminCommandEnum.unknown) return false;
//        // 去除空格存放到accountList中
//        for(String account: pendingAccountList){
//            if (account.trim().length() == 0) continue;
//            this.accountList.add(account.trim());
//        }
//        // 引用消息
//        Message replayMessage = update.getMessage().getReplyToMessage();
//        // 获取引用里的用户
//        if(replayMessage!=null){
//            String account = replayMessage.getFrom().getUserName();
//            this.accountList.add(account);
//        }
//        // 获取当前用户
//        this.operator = update.getMessage().getFrom().getUserName();
//
//        return true;
//    }
//
//    @Override
//    public void execute() {
//        if(this.accountList.size() == 0){
//            telGroup.notifyMessage("请@被操作对象");
//            return;
//        }
//
//        // 需要权限人权限
//        if (!this.checkPermission(this.operator, GroupAdminEnum.admin)) return;
//
//        if(this.commandType == SetAdminCommandEnum.cancelAdmin || commandType == SetAdminCommand.cancelOperator){
//            // 取消操作
//            this.cancelAdmins();
//        } else {
//            this.addAdmins();
//        }
//
//        this.telGroup.notifyMessage("操作成功");
//    }
//
//    private void cancelAdmins(){
//        BotGroupAdminManager groupAdminManager = this.telGroup.getAdminManager();
//        String groupOwnerAccount = this.telGroup.getOwnerUser().getAccount();
//        for (String account : accountList) {
//            if (groupOwnerAccount.equals(account)) continue;
//            groupAdminManager.remove(account);
//        }
//    }
//
//    private void addAdmins(){
//        BotGroupAdminManager groupAdminManager = this.telGroup.getAdminManager();
//        String groupOwnerAccount = this.telGroup.getOwnerUser().getAccount();
////        for (String account : accountList) {
////            if (groupOwnerAccount.equals(account)) continue;
////            GroupAdmin groupAdmin = groupAdminManager.get(account);
////            if(groupAdmin==null){
////                groupAdmin = new GroupAdmin();
////                groupAdmin.setAccount(account);
////                groupAdmin.setGroupId(this.telGroup.getGroup().getId());
////            }
////            groupAdmin.setPermission( this.commandType == SetAdminCommand.setAdmin ? GroupAdminEnum.admin.getValue() : GroupAdminEnum.operator.getValue());
////            groupAdminManager.add(groupAdmin);
////        }
//    }
}