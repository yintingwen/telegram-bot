package com.dething.telbot.newBase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dething.telbot.base.entity.GroupAdmin;
import com.dething.telbot.newBase.mapper.GroupAdminMapper;
import com.dething.telbot.newBase.service.IGroupAdminService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GroupAdminService extends ServiceImpl<GroupAdminMapper, GroupAdmin> implements IGroupAdminService {
    @Override
    public List<GroupAdmin> getAdminList(int groupId) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id", groupId);
        return this.list(queryWrapper);
    }

    public void addAdmin(int groupId, String account, Integer permission){
        GroupAdmin groupAdmin = new GroupAdmin();
        groupAdmin.setAccount(account);
        groupAdmin.setGroupId(groupId);
        groupAdmin.setPermission(permission);
        this.save(groupAdmin);
    }

    public void removeAdmin(int groupId, String account){
        QueryWrapper<GroupAdmin> queryWrapper = new QueryWrapper();
        queryWrapper.eq("group_id",groupId).eq("account", account);
        this.remove(queryWrapper);
    }
}
