/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * 
 * 项目名：	newretail-sys-api
 * 文件名：	LoginResponse.java
 * 模块说明：	
 * 修改历史：
 * 2018年2月6日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.bean.dto.user;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录返回数据
 * 
 * @author Debenson
 * @since 0.1
 */
@Data
@ApiModel(description = "登录返回数据")
public class LoginResponse implements Serializable {
  private static final long serialVersionUID = 8062141822462382599L;

  /**
   * 请求TOKEN, 请将其添加到业务请求Header中， 类似: request.addHeader("access_token", token);
   */
  @ApiModelProperty(
      value = "请求令牌, 请将其添加到业务请求Header中， 类似: request.addHeader(\"access-token\", token);",
      readOnly = true)
  private String accessToken;

  /** TOKEN有效期，单位秒 */
  @ApiModelProperty(value = "请求令牌有效期，单位秒", readOnly = true)
  private Integer expiresIn;

  /**
   * REFRESH-TOKEN有效期，单位秒
   */
  @ApiModelProperty(value = "刷新请求令牌的令牌", readOnly = true)
  private String refreshToken;

  /**
   * refresh-token有效期，单位秒
   */
  @ApiModelProperty(value = "刷新令牌有效期,单位秒", readOnly = true)
  private Integer refreshExpiresIn;

}
