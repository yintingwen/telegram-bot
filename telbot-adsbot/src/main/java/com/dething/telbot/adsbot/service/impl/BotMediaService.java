package com.dething.telbot.adsbot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dething.telbot.adsbot.entity.BotMedia;
import com.dething.telbot.adsbot.mapper.BotMediaMapper;
import com.dething.telbot.adsbot.service.IBotMediaService;
import org.springframework.stereotype.Service;


@Service
public class BotMediaService extends ServiceImpl<BotMediaMapper, BotMedia> implements IBotMediaService {
}
