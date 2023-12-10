package com.dething.telbot.base.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class RechargeNotify implements Serializable {
    private static final long serialVersionUID = 1L;

    private String chain;
    private String symbol;
    private int uid;
    private String address;
    private int token_id;
    private String hash;
    private String amount;
    private int app_id;
    private String sign;
}
