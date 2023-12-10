package com.dething.telbot.newBase.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dething.telbot.base.entity.Bot;
import com.dething.telbot.newBase.mapper.BotMapper;
import com.dething.telbot.newBase.service.IBotService;
import org.springframework.stereotype.Service;


@Service
public class BotService extends ServiceImpl<BotMapper, Bot> implements IBotService {
}
