/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * 
 * 项目名：	newretail-sys
 * 文件名：	ModifyPasswordDTO.java
 * 模块说明：	
 * 修改历史：
 * 2018年4月23日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.bean.dto.user;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 修改密码请求
 * 
 * @author Debenson
 * @since 0.1
 */
@Data
@ApiModel(description = "修改密码请求")
public class ModifyPasswordDTO implements Serializable {
  private static final long serialVersionUID = 9073028339816827418L;

  /**
   * 原密码
   */
  @NotBlank(message = "原密码长度必须大于6小于10个字符")
  @Size(min = 6, max = 10, message = "原密码长度必须大于6小于10个字符")
  @ApiModelProperty(value = "原密码, 密码长度必须大于6小于10个字符", required = true,
      allowableValues = "range[6,10]")
  private String oldPassword;

  /**
   * 新密码
   */
  @NotBlank(message = "新密码长度必须大于6小于10个字符")
  @Size(min = 6, max = 10, message = "新密码长度必须大于6小于10个字符")
  @ApiModelProperty(value = "新密码, 密码长度必须大于6小于10个字符", required = true,
      allowableValues = "range[6,10]")
  private String newPassword;

}
