package com.dething.telbot.adsbot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dething.telbot.adsbot.entity.BotAds;
import com.dething.telbot.adsbot.mapper.BotAdsMapper;
import com.dething.telbot.adsbot.service.IBotAdsService;
import org.springframework.stereotype.Service;


@Service
public class BotAdsService extends ServiceImpl<BotAdsMapper, BotAds> implements IBotAdsService {

}
