/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-dao
 * 文件名：	UpdateUserDTO.java
 * 模块说明：
 * 修改历史：
 * 2018年2月6日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.bean.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import com.gomore.experiment.commons.dao.dto.BaseDTO;
import com.gomore.newretail.sys.dao.po.EnumGender;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 更新会员
 *
 * @author Debenson
 * @since 0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "更新会员")
public class UpdateUserDTO extends BaseDTO {
  private static final long serialVersionUID = -7392949661638594722L;

  /**
   * 性别
   */
  @ApiModelProperty("性别")
  private EnumGender gender;

  /**
   * 手机号
   */
  @Size(max = 11, message = "手机号必须为11位数字")
  @ApiModelProperty(value = "手机号", allowableValues = "range[0,11]")
  private String mobile;

  /**
   * 真实姓名
   */
  @Size(max = 20, message = "真实姓名长度不能大于20")
  @ApiModelProperty(value = "真实姓名", allowableValues = "range[0,20]")
  private String realName;

  /**
   * 生日，格式yyyy-MM-dd
   */
  @Size(max = 10, message = "生日，格式yyyy-MM-dd")
  @ApiModelProperty(value = "生日，格式yyyy-MM-dd", allowableValues = "range[0,10]")
  private String birthday;

  /**
   * 身份证号
   */
  @Size(max = 30, message = "身份证号")
  @ApiModelProperty(value = "身份证号", allowableValues = "range[0,30]")
  private String idNo;

  /**
   * 邮箱
   */
  @Email
  @Length(max = 100)
  @ApiModelProperty(value = "邮箱", allowableValues = "range[0,100]")
  private String email;

  /**
   * 头像URL
   */
  @URL
  @Length(max = 256)
  @ApiModelProperty(value = "头像URL", allowableValues = "range[0,256]")
  private String avatar;
  
  /**
   * 工号
   */
  @Length(max = 30)
  @ApiModelProperty(value = "工号", allowableValues = "range[0, 30]")
  private String workNumber;

}
