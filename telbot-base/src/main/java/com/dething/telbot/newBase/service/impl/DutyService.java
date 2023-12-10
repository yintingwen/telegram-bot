package com.dething.telbot.newBase.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dething.telbot.base.entity.Duty;
import com.dething.telbot.newBase.mapper.DutyMapper;
import com.dething.telbot.newBase.service.IDutyService;
import org.springframework.stereotype.Service;


@Service
public class DutyService extends ServiceImpl<DutyMapper, Duty> implements IDutyService {
}
