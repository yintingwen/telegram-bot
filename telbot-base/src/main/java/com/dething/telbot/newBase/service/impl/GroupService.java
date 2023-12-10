package com.dething.telbot.newBase.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dething.telbot.base.entity.Group;
import com.dething.telbot.newBase.mapper.GroupMapper;
import com.dething.telbot.newBase.service.IGroupService;
import org.springframework.stereotype.Service;

@Service
public class GroupService extends ServiceImpl<GroupMapper, Group> implements IGroupService {
    public Group saveGroup(Group group){
        int id = this.baseMapper.insert(group);
        if(id <= 0) return null;

        return group;
    }
}
