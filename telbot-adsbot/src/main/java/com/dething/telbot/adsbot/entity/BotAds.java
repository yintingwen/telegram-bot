package com.dething.telbot.adsbot.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
public class BotAds {

  /**
   * 主键
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  private int botId;
  private int userId;
  private int groupId;
  private String tag;
  private String text;
  private String link;

  @TableField(fill = FieldFill.INSERT)
  private Date createTime;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;

  @TableLogic
  private int isDeleted;

}
