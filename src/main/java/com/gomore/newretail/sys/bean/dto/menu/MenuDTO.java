/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-dao
 * 文件名：	UserVO.java
 * 模块说明：
 * 修改历史：
 * 2018年2月2日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.bean.dto.menu;

import com.gomore.experiment.commons.dao.dto.StandardDTO;
import com.gomore.newretail.sys.dao.po.EnumMenuType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统权限
 *
 * @author Debenson
 * @since 0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "菜单")
public class MenuDTO extends StandardDTO {
  private static final long serialVersionUID = -9066885559131050582L;

  /**
   * 上级菜单ID，一级菜单为null
   */
  @ApiModelProperty(value = "上级菜单ID，一级菜单为null")
  private Long parentId;

  /**
   * 菜单名称
   */
  @ApiModelProperty("菜单名称")
  private String name;

  /**
   * 菜单URL
   */
  @ApiModelProperty("菜单URL")
  private String url;

  /**
   * 权限(多个用逗号分隔，如：user:list,user:create)
   */
  @ApiModelProperty("权限(多个用逗号分隔，如：user:list,user:create)")
  private String permissions;

  /**
   * 菜单类型
   */
  @ApiModelProperty("菜单类型")
  private EnumMenuType type;

  /**
   * 菜单图标
   */
  @ApiModelProperty("菜单图标")
  private String icon;

  /**
   * 排序
   */
  @ApiModelProperty("排序")
  private Integer orderNum;

}
