/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-api
 * 文件名：	LoginRequest.java
 * 模块说明：
 * 修改历史：
 * 2018年2月6日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.bean.dto.user;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录请求数据
 *
 * @author Debenson
 * @since 0.1
 */
@ApiModel(description = "登录请求数据")
@Data
public class LoginRequest implements Serializable {
  private static final long serialVersionUID = 7035897325475881223L;

  /**
   * 账户名称
   */
  @NotEmpty(message = "账户名称不能为空")
  @ApiModelProperty(value = "账户名称，可以为账户ID、手机号或邮箱", required = true)
  private String username;
  /**
   * 账户密码
   */
  @NotEmpty(message = "账户密码")
  @ApiModelProperty(value = "账户密码", required = true)
  private String password;
  /**
   * 工作组织，如果有多个工作组织需要指定当前工作组织ID，如果只有一个工作组织可以不传
   */
  @ApiModelProperty(value = "工作组织", required = false)
  private Long workingOrgId;

}
