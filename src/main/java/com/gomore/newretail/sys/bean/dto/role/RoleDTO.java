/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * 
 * 项目名：	newretail-sys-dao
 * 文件名：	RoleDTO.java
 * 模块说明：	
 * 修改历史：
 * 2018年2月11日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.bean.dto.role;

import com.gomore.experiment.commons.dao.dto.StandardDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色DTO
 * 
 * @author Debenson
 * @since 0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "角色")
public class RoleDTO extends StandardDTO {
  private static final long serialVersionUID = 2819531237146622696L;

  /**
   * 所属组织名称
   */
  @ApiModelProperty(value = "所属组织名称", required = true)
  private String orgName;

  /**
   * 角色名称
   */
  @ApiModelProperty(value = "角色名称", required = true)
  private String name;

  /**
   * 备注
   */
  @ApiModelProperty(value = "备注")
  private String remark;

}
