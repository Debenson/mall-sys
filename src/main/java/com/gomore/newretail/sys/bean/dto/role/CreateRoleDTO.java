/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-api
 * 文件名：	CreateRoleDTO.java
 * 模块说明：
 * 修改历史：
 * 2018年2月11日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.bean.dto.role;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增角色请求
 *
 * @author Debenson
 * @since 0.1
 */
@ApiModel(description = "新增角色请求")
@Data
public class CreateRoleDTO implements Serializable {
  private static final long serialVersionUID = 7212712988480121828L;

  /**
   * 所属组织ID
   */
  @NotNull
  @ApiModelProperty(value = "所属组织ID", required = true)
  private Long orgId;

  /**
   * 角色名称
   */
  @NotEmpty
  @ApiModelProperty(value = "角色名称", required = true, allowableValues = "range[1,30]")
  private String name;

  /**
   * 备注
   */
  @Size(max = 128)
  @ApiModelProperty(value = "备注", allowableValues = "range[1,128]")
  private String remark;

}
