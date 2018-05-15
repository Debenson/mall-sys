/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * 
 * 项目名：	newretail-sys-api
 * 文件名：	UpdateRolePermission.java
 * 模块说明：	
 * 修改历史：
 * 2018年2月11日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.bean.dto.role;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 更新角色权限请求
 * 
 * @author Debenson
 * @since 0.1
 */
@ApiModel(description = "更新角色权限请求")
@Data
public class UpdateRolePermissionDTO implements Serializable {
  private static final long serialVersionUID = -7051188330070103197L;

  /**
   * 角色ID
   */
  @NotNull
  @ApiModelProperty(value = "角色ID", required = true)
  private Long roleId;

  /**
   * 权限ID列表，如果传空或空集合将清空角色的权限集合
   */
  @ApiModelProperty(value = "权限ID列表，如果传空或空集合将清空角色的权限集合", dataType="")
  private Long[] permissionIds;

}
