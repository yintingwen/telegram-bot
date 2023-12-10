package com.dething.telbot.newBase.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dething.telbot.base.entity.BuyPlan;
import com.dething.telbot.newBase.mapper.BuyPlanMapper;
import com.dething.telbot.newBase.service.IBuyPlanService;
import org.springframework.stereotype.Service;


@Service
public class BuyPlanService extends ServiceImpl<BuyPlanMapper, BuyPlan> implements IBuyPlanService {

}
