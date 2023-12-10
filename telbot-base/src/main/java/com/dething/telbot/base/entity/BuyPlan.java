package com.dething.telbot.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
public class BuyPlan {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  private Integer botId;

  private Integer dutyId;

  private Integer userId;

  private Float price;

  private Integer months;

  @TableField(fill = FieldFill.INSERT)
  private Date createTime;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;

  @TableLogic
  private Integer isDeleted;
}
