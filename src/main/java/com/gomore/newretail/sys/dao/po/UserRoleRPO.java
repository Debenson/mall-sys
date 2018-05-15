package com.gomore.newretail.sys.dao.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.gomore.experiment.commons.dao.po.BaseRPO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户与角色的关系 <br>
 * 
 * @author Debenson
 * @since 0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "nr_sys_user_role_rl")
public class UserRoleRPO extends BaseRPO<UserRoleRPO> {
  private static final long serialVersionUID = -556013627440811930L;

  /**
   * 用户ID
   */
  private Long userId;

  /**
   * 组织ID
   */
  private Long roleId;

  /**
   * 工作组织ID
   */
  private Long workingOrgId;

}
