package com.dething.telbot.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class User {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  private int botId;

  private String account;

  // 私信聊天ID
  private Long chatId;

  private LocalDateTime expireTime;

  private String rechargeAddress;

  private float amount;

  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;

  @TableLogic
  private Integer isDeleted;
}
