package com.dething.telbot.newBase.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dething.telbot.base.entity.User;
import com.dething.telbot.newBase.mapper.UserMapper;
import com.dething.telbot.newBase.service.IUserService;
import org.springframework.stereotype.Service;


@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {
    public String getName () {
        return "UserService";
    }
}
