package com.dething.telbot.base.entity;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(
        value = "recharge",
        fallback = IRechargeClientFallback.class
)
public interface IRechargeClient {
    String API_PREFIX = "/feign_recharge";

    /**
     * 获取地址
     * @param uid
     * @param chain
     * @return
     */
    @GetMapping(API_PREFIX + "/getAddress")
    String getAddress(@RequestParam("uid") Integer uid,@RequestParam("chain") String chain);
}
