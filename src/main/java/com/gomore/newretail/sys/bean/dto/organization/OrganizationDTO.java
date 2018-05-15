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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gomore.experiment.commons.dao.dto.StandardDTO;
import com.gomore.newretail.commons.auth.AuthorizedOrganization;
import com.gomore.newretail.sys.dao.po.EnumOrganizationType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 组织
 *
 * @author Debenson
 * @since 0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "组织")
public class OrganizationDTO extends StandardDTO implements AuthorizedOrganization {
  private static final long serialVersionUID = -5831954894278897970L;

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
   * 组织路径，结构类似: /上级组织id/本级组织id/下级组织id，用此结构可以比较简单的查出指定组织的所有下级组织。
   */
  @ApiModelProperty("组织路径，结构类似: /上级组织id/本级组织id/下级组织id，用此结构可以比较简单的查出指定组织的所有下级组织")
  private String path;

  /**
   * 联系电话
   */
  @ApiModelProperty("联系电话")
  private String tel;

  /**
   * 联系人
   */
  @ApiModelProperty("联系人")
  private String contact;

  /**
   * 地址
   */
  @ApiModelProperty("地址")
  private String address;

  /**
   * 经度
   */
  @ApiModelProperty("经度")
  private String longitude;

  /**
   * 纬度
   */
  @ApiModelProperty("纬度")
  private String latitude;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String remark;

  /**
   * 组织类型
   */
  @Override
  @JsonIgnore
  @ApiModelProperty(hidden = true)
  public String getOrgType() {
    return type == null ? null : type.name();
  }
}
