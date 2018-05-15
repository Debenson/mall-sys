/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * 
 * 项目名：	newretail-sys-api
 * 文件名：	InsertMenuDTO.java
 * 模块说明：	
 * 修改历史：
 * 2018年2月10日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.bean.dto.menu;

import org.hibernate.validator.constraints.Length;

import com.gomore.experiment.commons.dao.dto.BaseDTO;
import com.gomore.newretail.sys.dao.po.EnumMenuType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 更新菜单请求
 * 
 * @author Debenson
 * @since 0.1
 */
@ApiModel(description = "新增菜单请求")
@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateMenuDTO extends BaseDTO {
  private static final long serialVersionUID = 3016593518490329184L;

  /**
   * 父菜单ID，一级菜单为null
   */
  @ApiModelProperty(value = "父菜单ID，一级菜单为null")
  private Long parentId;

  /**
   * 菜单类型
   */
  @ApiModelProperty("菜单类型")
  private EnumMenuType type;

  /**
   * 菜单名称
   */
  @Length(max = 30, message = "菜单名称长度不能大于30个字符")
  @ApiModelProperty("菜单名称")
  private String name;

  /**
   * 菜单URL
   */
  @ApiModelProperty("菜单URL，当类型等于=MENU不能为空，其它可空")
  @Length(max = 200, message = "菜单URL长度不能大于200个字符")
  private String url;

  /**
   * 权限(多个用逗号分隔，如：user:list,user:create)
   */
  @ApiModelProperty("权限(多个用逗号分隔，如：user:list,user:create)")
  @Length(max = 500, message = "权限长度不能大于500个字符")
  private String permissions;

  /**
   * 菜单图标
   */
  @ApiModelProperty("菜单图标")
  @Length(max = 50, message = "菜单图标长度不能大于50个字符")
  private String icon;

  /**
   * 序号，序号越小越靠前
   */
  @ApiModelProperty("序号，序号越小越靠前")
  private Integer orderNum;

}
