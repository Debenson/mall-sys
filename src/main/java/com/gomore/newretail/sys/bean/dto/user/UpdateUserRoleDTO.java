/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-api
 * 文件名：	UpdateUserRoleDTO.java
 * 模块说明：
 * 修改历史：
 * 2018年2月11日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.bean.dto.user;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 更新用户角色请求
 *
 * @author Debenson
 * @since 0.1
 */
@ApiModel(description = "更新用户角色请求")
@Data
public class UpdateUserRoleDTO implements Serializable {
  private static final long serialVersionUID = -7051188330070103197L;

  /**
   * 用户ID
   */
  @NotNull
  @ApiModelProperty(value = "用户ID", required = true)
  private Long userId;

  /**
   * 角色ID列表，如果传空或空集合将清空用户的角色集合
   */
  @ApiModelProperty(value = "角色ID列表，如果传空或空集合将清空用户的角色集合")
  @NotNull
  private Long[] roleIds;

}
