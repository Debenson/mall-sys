package com.gomore.newretail.sys.dao.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.gomore.experiment.commons.dao.po.BaseRPO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 组织角色关系表
 *
 * @author Debenson
 * @since 0.1
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "nr_sys_org_role_rl")
public class OrganizationRoleRPO extends BaseRPO<OrganizationRoleRPO> {
  private static final long serialVersionUID = -6859561756489084674L;

  /**
   * 组织ID
   */
  @TableField("org_id")
  private Long organizationId;

  /**
   * 角色ID
   */
  private Long roleId;

}
