package com.dething.telbot.newBase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dething.telbot.base.entity.GroupAdmin;

import java.util.List;


public interface IGroupAdminService extends IService<GroupAdmin> {
    List<GroupAdmin> getAdminList(int groupId);

    /**
     * 添加管理员
     * @param groupId
     * @param account
     */
    void addAdmin(int groupId, String account, Integer permission);

    /**
     * 取消管理员
     * @param groupId
     * @param account
     */
    void removeAdmin(int groupId, String account);

}
