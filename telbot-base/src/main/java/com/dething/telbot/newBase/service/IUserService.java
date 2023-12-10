package com.dething.telbot.newBase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dething.telbot.base.entity.User;

public interface IUserService extends IService<User> {
    String getName ();
}
