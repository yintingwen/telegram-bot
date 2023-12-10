package com.dething.telbot.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
public class Recharge {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  private Integer botId;

  private Integer userId;

  private Float amount;

  @TableField(fill = FieldFill.INSERT)
  private Date createTime;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;

  @TableLogic
  private Integer isDeleted;
}
