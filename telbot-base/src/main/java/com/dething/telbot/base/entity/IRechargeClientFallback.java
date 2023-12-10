package com.dething.telbot.base.entity;

import org.springframework.stereotype.Component;

@Component
public class IRechargeClientFallback implements IRechargeClient {

    @Override
    public String getAddress(Integer uid, String chain){
        return null;
    }
}
