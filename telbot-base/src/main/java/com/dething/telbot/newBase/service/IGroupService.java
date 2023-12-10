package com.dething.telbot.newBase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dething.telbot.base.entity.Group;

public interface IGroupService extends IService<Group> {
    Group saveGroup(Group group);
}
