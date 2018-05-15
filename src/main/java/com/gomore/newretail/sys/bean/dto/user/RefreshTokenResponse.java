package com.gomore.newretail.sys.bean.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liyang
 * @since 0.1
 */
@Data
@ApiModel(description = "token刷新返回数据")
public class RefreshTokenResponse implements Serializable {
  
  private static final long serialVersionUID = 8832637208906663228L;
  /**
   * 请求TOKEN, 请将其添加到业务请求Header中， 类似: request.addHeader("access-token", token);
   */
  @ApiModelProperty(
          value = "请求TOKEN, 请将其添加到业务请求Header中， 类似: request.addHeader(\"access-token\", token);",
          readOnly = true)
  private String accessToken;
  
  /**
   * TOKEN有效期，单位秒
   */
  @ApiModelProperty(value = "TOKEN有效期，单位秒", readOnly = true)
  private Integer expiresIn;
  
  
  /**
   * REFRESH-TOKEN有效期，单位秒
   */
  @ApiModelProperty(value = "refresh-token", readOnly = true)
  private String refreshToken;
  
  
  /**
   * refresh-token有效期，单位秒
   */
  @ApiModelProperty(value = "refreshToken,单位秒", readOnly = true)
  private Integer refreshExpiresIn;
}
