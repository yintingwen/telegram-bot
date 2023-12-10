package com.dething.telbot.newBase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dething.telbot.base.entity.DutyPlan;
import com.dething.telbot.newBase.mapper.DutyPlanMapper;
import com.dething.telbot.newBase.service.IDutyPlanService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DutyPlanService extends ServiceImpl<DutyPlanMapper, DutyPlan> implements IDutyPlanService {
    @Override
    public List<DutyPlan> findByDutyId(Long dutyId) {
        QueryWrapper<DutyPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("duty_id", dutyId);
        return list(queryWrapper);
    }
}
