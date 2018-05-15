package com.gomore.newretail.sys.dao.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.gomore.experiment.commons.dao.po.StandardEntity;

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
@TableName(value = "nr_sys_org")
public class OrganizationPO extends StandardEntity {
  private static final long serialVersionUID = 3392240626956006396L;

  /**
   * 根组织ID
   */
  public static final Long ROOT_ORG_ID = 0L;
  public static final String ROOT_ORG_NAME = "超级组织";

  /**
   * 组织代码
   */
  private String code;

  /**
   * 组织名称
   */
  private String name;

  /**
   * 组织类型
   */
  private EnumOrganizationType type;

  /**
   * 组织状态，是否被禁用。禁用后该组织下所有人员均不能登录系统。
   */
  private Boolean disabled;

  /**
   * 组织路径，结构类似: /上级组织id/本级组织id/下级组织id，用此结构可以比较简单的查出指定组织的所有下级组织。
   */
  private String path;

  /**
   * 联系电话
   */
  private String tel;

  /**
   * 联系人
   */
  private String contact;

  /**
   * 地址
   */
  private String address;

  /**
   * 经度
   */
  private String longitude;

  /**
   * 纬度
   */
  private String latitude;

  /**
   * 备注
   */
  private String remark;

}
