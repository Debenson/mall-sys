/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * 
 * 项目名：	newretail-sys-api
 * 文件名：	CreateRoleDTO.java
 * 模块说明：	
 * 修改历史：
 * 2018年2月11日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.bean.dto.role;

import javax.validation.constraints.Size;

import com.gomore.experiment.commons.dao.dto.BaseDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 更新角色请求
 * 
 * @author Debenson
 * @since 0.1
 */
@ApiModel(description = "更新角色请求")
@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateRoleDTO extends BaseDTO {
  private static final long serialVersionUID = 7212712988480121828L;

  /**
   * 角色名称
   */
  @ApiModelProperty(value = "角色名称", allowableValues = "range[1,30]")
  private String name;

  /**
   * 备注
   */
  @Size(max = 128)
  @ApiModelProperty(value = "备注", allowableValues = "range[1,128]")
  private String remark;

}
