package com.dething.telbot.base.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.dething.cloud.common.core.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 *
 */
@Data
@TableName("tel_user")

public class BotUser implements Serializable {

    private static final long serialVersionUID = 3202198743628503610L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 机器人ID
     */
    private Integer botId;

    /**
     * 账号
     */
    private String account;

    /**
     * 余额
     */
    private Float amount;

    /**
     * 聊天ID
     */
    private Long chatId;

    /**
     * 充值地址
     */
    private String rechargeAddress;


    /**
     * 过期时间
     */
    @DateTimeFormat(pattern = DateUtil.YYYY_MM_DD_HH_MM_SS)
    @JsonFormat(pattern = DateUtil.YYYY_MM_DD_HH_MM_SS)
    private Date expireTime;



    @TableField(fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = DateUtil.YYYY_MM_DD_HH_MM_SS)
    @JsonFormat(pattern = DateUtil.YYYY_MM_DD_HH_MM_SS)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = DateUtil.YYYY_MM_DD_HH_MM_SS)
    @JsonFormat(pattern = DateUtil.YYYY_MM_DD_HH_MM_SS)
    private Date updateTime;

    @TableLogic
    private Integer isDeleted;

}
