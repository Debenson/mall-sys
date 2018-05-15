package com.gomore.newretail.sys.bean.dto.user;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liyang
 * @since 0.1
 */
@Data
@ApiModel(description = "token刷新")
public class RefreshTokenRequest implements Serializable {
  private static final long serialVersionUID = 8832637208906663228L;

  /**
   * 刷新Token
   */
  @ApiModelProperty(value = "刷新Token", required = true)
  @NotNull(message = "刷新token不能为空")
  private String refreshToken;

}
