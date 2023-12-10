package com.dething.telbot.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;


@Data
public class GroupAdmin {
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  private long groupId;
  private String account;

  private Integer permission;

  @TableField(fill = FieldFill.INSERT)
  private Date createTime;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;

  @TableLogic
  private long isDeleted;

}
