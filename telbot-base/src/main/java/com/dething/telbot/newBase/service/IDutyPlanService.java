package com.dething.telbot.newBase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dething.telbot.base.entity.DutyPlan;

import java.util.List;

public interface IDutyPlanService extends IService<DutyPlan> {

    List<DutyPlan> findByDutyId(Long dutyId);
}
