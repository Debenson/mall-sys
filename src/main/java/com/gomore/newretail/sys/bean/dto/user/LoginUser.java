/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * 
 * 项目名：	newretail-sys-api
 * 文件名：	LoginUser.java
 * 模块说明：	
 * 修改历史：
 * 2018年2月6日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.bean.dto.user;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gomore.newretail.commons.auth.AuthorizedUser;
import com.gomore.newretail.commons.auth.EnumUserType;
import com.gomore.newretail.sys.bean.dto.organization.OrganizationDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录用户信息
 * 
 * @author Debenson
 * @since 0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "登录用户信息")
public class LoginUser extends UserDTO implements AuthorizedUser {
  private static final long serialVersionUID = 7994487967008683771L;

  /**
   * 当前用户的工作组织
   */
  @ApiModelProperty("当前用户的工作组织")
  private OrganizationDTO workingOrg;

  /**
   * 最后登录时间
   */
  @ApiModelProperty("最后登录时间 ")
  private Date lastLoginTime;

  /**
   * 最后登录IP
   */
  @ApiModelProperty("最后登录IP")
  private String lastLoginIp;

  /**
   * 拥有的权限列表
   */
  @ApiModelProperty("拥有的权限列表")
  private Set<String> permissions;

  /** 隐藏用户密码 */
  @Override
  @JsonIgnore
  @ApiModelProperty(hidden = true)
  public String getPassword() {
    return super.getPassword();
  }

  @Override
  @JsonIgnore
  @ApiModelProperty(hidden = true)
  public EnumUserType getUserType() {
    return EnumUserType.USER;
  }

  @Override
  @JsonIgnore
  @ApiModelProperty(hidden = true)
  public String getName() {
    return getUsername();
  }

}
