/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-api
 * 文件名：	InsertMenuDTO.java
 * 模块说明：
 * 修改历史：
 * 2018年2月10日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.bean.dto.organization;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import javax.validation.constraints.NotEmpty;

import com.gomore.newretail.sys.dao.po.EnumOrganizationType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增组织请求
 *
 * @author Debenson
 * @since 0.1
 */
@ApiModel(description = "新增组织请求")
@Data
public class CreateOrganizationDTO implements Serializable {
  private static final long serialVersionUID = -3020268761257130144L;

  /**
   * 上级组织ID
   */
  @ApiModelProperty(value = "上级组织ID")
  private Long upperOrgId;

  /**
   * 组织代码
   */
  @NotEmpty(message = "组织代码不能为空")
  @ApiModelProperty(value = "组织代码", required = true, allowableValues = "range[1,20]")
  private String code;

  /**
   * 组织名称
   */
  @NotEmpty(message = "组织名称不能为空")
  @ApiModelProperty(value = "组织名称", required = true, allowableValues = "range[1,30]")
  private String name;

  /**
   * 组织类型
   */
  @NotNull(message = "组织类型不能为空")
  @ApiModelProperty(value = "组织类型", required = true)
  private EnumOrganizationType type;

  /**
   * 联系电话
   */
  @ApiModelProperty(value = "联系电话", allowableValues = "range[1,20]")
  private String tel;

  /**
   * 联系人
   */
  @ApiModelProperty(value = "联系人", allowableValues = "range[1,20]")
  private String contact;

  /**
   * 地址
   */
  @ApiModelProperty(value = "地址", allowableValues = "range[1,150]")
  private String address;

  /**
   * 经度
   */
  @ApiModelProperty(value = "经度", allowableValues = "range[1,20]")
  private String longitude;

  /**
   * 纬度
   */
  @ApiModelProperty(value = "纬度", allowableValues = "range[1,20]")
  private String latitude;

  /**
   * 备注
   */
  @ApiModelProperty(value = "备注", allowableValues = "range[1,128]")
  private String remark;

}
