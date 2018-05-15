package com.gomore.newretail.sys.dao.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.gomore.experiment.commons.dao.po.BaseRPO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色权限关系表
 *
 * @author Debenson
 * @since 0.1
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "nr_sys_role_permission_rl")
public class RolePermissionRPO extends BaseRPO<RolePermissionRPO> {
  private static final long serialVersionUID = 3731389109945608183L;

  /**
   * 角色ID
   */
  private Long roleId;

  /**
   * 权限ID
   */
  private Long permissionId;

}
