/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-dao
 * 文件名：	OrganizationDTO.java
 * 模块说明：
 * 修改历史：
 * 2018年2月11日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.bean.dto.organization;

import java.io.Serializable;

import com.gomore.newretail.sys.dao.po.EnumOrganizationType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组织及其下级组织数量
 *
 * @author tom
 */
@Data
@ApiModel(description = "组织及其下级组织数量")
public class OrganizationCountDTO implements Serializable {

  private static final long serialVersionUID = -7076890593850962789L;

  /**
   * 组织Id
   */
  @ApiModelProperty("组织Id")
  private Long id;
  /**
   * 组织代码
   */
  @ApiModelProperty("组织代码")
  private String code;

  /**
   * 组织名称
   */
  @ApiModelProperty("组织名称")
  private String name;

  /**
   * 组织类型
   */
  @ApiModelProperty("组织类型")
  private EnumOrganizationType type;

  /**
   * 组织状态，是否被禁用。禁用后该组织下所有人员均不能登录系统。
   */
  @ApiModelProperty("组织状态，是否被禁用。禁用后该组织下所有人员均不能登录系统。")
  private Boolean disabled;

  /**
   * 下级组织数量
   */
  @ApiModelProperty("下级组织数量")
  private Integer count;
}
