package com.gomore.newretail.sys.dao.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.gomore.experiment.commons.dao.po.BaseRPO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户与工作组织的关系 <br>
 * 用户的所有组织就是一种特殊的工作组织。每个用户除了可以工作在自己的所属组织（即授权所有组织的部分权限）外，还可以工作在其它组织。
 * 用户的工作组织可设置的范围是：操作人的组织及其所有下级组织（包括下级的下级）
 * 
 * @author Debenson
 * @since 0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "nr_sys_user_working_org_rl")
public class UserWorkingOrganizationRPO extends BaseRPO<UserWorkingOrganizationRPO> {
  private static final long serialVersionUID = -556013627440811930L;

  /**
   * 用户ID
   */
  private Long userId;

  /**
   * 工作组织ID
   */
  private Long workingOrgId;

}
