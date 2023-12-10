package com.dething.telbot.newBase.manager;

import com.dething.cloud.common.core.util.EnumUtil;
import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.base.entity.GroupAdmin;
import com.dething.telbot.base.enums.GroupAdminEnum;
import com.dething.telbot.newBase.service.impl.GroupAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotGroupAdminManager implements ApplicationRunner {

    @Autowired
    GroupAdminService groupAdminService;

    /**
     * 群组ID和accountMap的映射
     */
    public final Map<Long, Map<String, GroupAdmin>> groupAdminMap = new HashMap<>();

    /**
     * 添加管理员
     * @param groupAdmin 管理员对象
     */
    public void addAmin(GroupAdmin groupAdmin){
        Map<String, GroupAdmin> accountMap = this.groupAdminMap.computeIfAbsent(groupAdmin.getGroupId(), k -> new HashMap<>());
        accountMap.put(groupAdmin.getAccount(), groupAdmin);
        this.groupAdminService.saveOrUpdate(groupAdmin);
    }

    /**
     * 移除管理员
     * @param groupId 群id
     * @param account 账号名
     */
    public void removeAdmin(Long groupId, String account){
        Map<String, GroupAdmin> accountMap = this.groupAdminMap.get(groupId);
        if(accountMap == null) return;
        accountMap.remove(account);
    }

    /**
     * 校验是否有权限
     * @param account 操作者账号名
     * @return boolean
     */
    protected boolean checkPermission(Long groupId, String account){
        Map<String, GroupAdmin> accountMap = this.groupAdminMap.get(groupId);
        GroupAdmin adminAccount = accountMap.get(account);
        return adminAccount != null && EnumUtil.containsPropertyValue(GroupAdminEnum.class, GroupAdminEnum::getValue, adminAccount.getPermission());
    }

    /**
     * 检验账号是否有某个权限
     * @param groupId 群id
     * @param account 操作者账号名
     * @param permission 权限
     * @return boolean
     */
    protected boolean checkPermission(Long groupId, String account, GroupAdminEnum permission){
        Map<String, GroupAdmin> accountMap = this.groupAdminMap.get(groupId);
        if(accountMap == null) return false;
        GroupAdmin adminAccount = accountMap.get(account);
        return adminAccount != null && adminAccount.getPermission() == permission.getValue();
    }

    public void load () {
        this.groupAdminService = SpringUtil.getBean(GroupAdminService.class);
        List<GroupAdmin> groupAdminList = this.groupAdminService.list();
        groupAdminList.forEach((admin) -> {
            Map<String, GroupAdmin> accountMap = this.groupAdminMap.computeIfAbsent(admin.getGroupId(), k -> new HashMap<>());
            accountMap.put(admin.getAccount(), admin);
        });
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.load();
    }
}
