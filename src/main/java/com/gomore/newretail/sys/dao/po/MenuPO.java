package com.gomore.newretail.sys.dao.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.gomore.experiment.commons.dao.po.StandardEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单
 *
 * @author Debenson
 * @since 0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "nr_sys_menu")
public class MenuPO extends StandardEntity {
  private static final long serialVersionUID = 1318757847833944487L;

  /**
   * 菜单类型
   */
  private EnumMenuType type;

  /**
   * 父菜单ID，一级菜单为null
   */
  private Long parentId;

  /**
   * 菜单名称
   */
  private String name;

  /**
   * 菜单URL
   */
  private String url;

  /**
   * 权限(多个用逗号分隔，如：user:list,user:create)
   */
  private String permissions;

  /**
   * 菜单图标
   */
  private String icon;

  /**
   * 排序
   */
  private Integer orderNum;

}
