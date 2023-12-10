package com.dething.telbot.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class Bot implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String token;

    private Map<String, Object> config;

    private int type;

    private String owner;

    private String account;

    private int isPrivate;

    private int proxyType;
    private String proxyHost;
    private int proxyPort;

    private String contacts;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic
    private int isDeleted;
}
