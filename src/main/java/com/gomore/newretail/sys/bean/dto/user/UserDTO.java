/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-dao
 * 文件名：	UserVO.java
 * 模块说明：
 * 修改历史：
 * 2018年2月2日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.bean.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gomore.experiment.commons.dao.dto.StandardDTO;
import com.gomore.newretail.sys.dao.po.EnumGender;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户
 *
 * @author Debenson
 * @since 0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户")
public class UserDTO extends StandardDTO {
  private static final long serialVersionUID = 3764129359700903715L;

  /**
   * 所属组织名称
   */
  @ApiModelProperty("所属组织名称")
  private String orgName;

  /**
   * 账户
   */
  @ApiModelProperty("账户")
  private String username;

  /**
   * 密码
   */
  @ApiModelProperty(value = "密码", hidden = true)
  @JsonIgnore
  private String password;

  /**
   * 是否被禁用
   */
  @ApiModelProperty("是否被禁用")
  private Boolean disabled;

  /**
   * 手机号
   */
  @ApiModelProperty("手机号")
  private String mobile;

  /**
   * 生成密码的盐
   */
  @ApiModelProperty(value = "生成密码的盐", hidden = true)
  @JsonIgnore
  private String salt;

  /**
   * 真实姓名
   */
  @ApiModelProperty(value = "真实姓名")
  private String realName;

  /**
   * 性别
   */
  @ApiModelProperty(value = "性别")
  private EnumGender gender;

  /**
   * 生日，格式yyyy-MM-dd
   */
  @ApiModelProperty(value = "生日，格式yyyy-MM-dd")
  private String birthday;

  /**
   * 身份证号
   */
  @ApiModelProperty(value = "身份证号")
  private String idNo;

  /**
   * 邮箱
   */
  @ApiModelProperty(value = "邮箱")
  private String email;

  /**
   * 头像URL
   */
  @ApiModelProperty(value = "头像URL")
  private String avatar;

  /**
   * 工号
   */
  @ApiModelProperty(value = "工号", allowableValues = "range[0, 30]")
  private String workNumber;
}
