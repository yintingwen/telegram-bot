package com.dething.telbot.base.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
@TableName(autoResultMap = true)
public class Group implements Serializable {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  private Integer botId;

  private Long chatId;

  private String owner;

  private Integer offline;

  /**
   * 群组配置信息
   */
  @TableField(typeHandler = JacksonTypeHandler.class)
  Map<String,Object> config;

  @TableField(fill = FieldFill.INSERT)
  private Date createTime;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;

  @TableLogic
  private Integer isDeleted;

}
