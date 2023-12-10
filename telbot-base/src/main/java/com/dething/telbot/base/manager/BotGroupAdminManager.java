package com.dething.telbot.base.manager;

import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.base.entity.Group;
import com.dething.telbot.base.entity.GroupAdmin;
import com.dething.telbot.newBase.service.IGroupAdminService;

import java.util.List;

public class BotGroupAdminManager {
    public BotGroupAdminManager(Group group) {
    }

    public GroupAdmin get(String operator) {
        return new GroupAdmin();
    }
//    private final IGroupAdminService groupAdminService;
//
//    public BotGroupAdminManager(Group group) {
//        groupAdminService = SpringUtil.getBean(IGroupAdminService.class);
//        List<GroupAdmin> groupAdminList = groupAdminService.getAdminList(group.getId());
//        groupAdminList.forEach((admin) -> this.managerMap.put(admin.getAccount(), admin));
//    }
//
//    /**
//     * 添加管理员
//     * @param groupAdmin 管理员
//     */
//    public void add(GroupAdmin groupAdmin){
//        this.add(groupAdmin.getAccount(), groupAdmin);
//    }
//
//    /**
//     * 添加管理员
//     * @param account 唯一标识
//     * @param groupAdmin 管理员
//     */
//    public void add(String account, GroupAdmin groupAdmin) {
//        this.groupAdminService.saveOrUpdate(groupAdmin);
//        this.managerMap.put(account, groupAdmin);
//    }
//
//    /**
//     * 移除管理员
//     * @param account 唯一标识
//     */
//    public void remove(String account){
//        GroupAdmin removeAccount = this.managerMap.get(account);
//        if(removeAccount == null) return;
//        groupAdminService.removeById(removeAccount.getId());
//        managerMap.remove(account);
//    }
//
//    public void get (String account) {}
}
