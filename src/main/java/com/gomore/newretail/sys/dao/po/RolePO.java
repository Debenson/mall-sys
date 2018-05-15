package com.gomore.newretail.sys.dao.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.gomore.experiment.commons.dao.po.StandardEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色
 * 
 * @author Debenson
 * @since 0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "nr_sys_role")
public class RolePO extends StandardEntity {
  private static final long serialVersionUID = 1199363339992139891L;

  /**
   * 角色名称
   */
  private String name;

  /**
   * 备注
   */
  private String remark;

}
