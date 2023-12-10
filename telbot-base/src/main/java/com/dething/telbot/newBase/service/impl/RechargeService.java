package com.dething.telbot.newBase.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dething.telbot.base.entity.Recharge;
import com.dething.telbot.newBase.mapper.RechargeMapper;
import com.dething.telbot.newBase.service.IRechargeService;
import org.springframework.stereotype.Service;


@Service
public class RechargeService extends ServiceImpl<RechargeMapper, Recharge> implements IRechargeService {

}
