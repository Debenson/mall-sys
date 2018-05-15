/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * 
 * 项目名：	newretail-sys
 * 文件名：	PermssionHistoryPO.java
 * 模块说明：	
 * 修改历史：
 * 2018年4月24日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.dao.po;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.gomore.experiment.commons.dao.po.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限历史表
 * 
 * @author Debenson
 * @since 0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "nr_sys_permission_hst")
public class PermissionHistoryPO extends BaseEntity {
  private static final long serialVersionUID = -4407857393825183560L;

  public static final int FIRST_VERSION = 0;

  /**
   * 版本号
   */
  @Version
  private Integer version;

  /**
   * 最后更新时间
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;

  /**
   * 当前权限文件的签名
   */
  private String signature;

}
