/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * 
 * 项目名：	newretail-sys-dao
 * 文件名：	EnumOrganizationType.java
 * 模块说明：	
 * 修改历史：
 * 2018年2月11日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.dao.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IEnum;

/**
 * 组织类型
 * 
 * @author Debenson
 * @since 0.1
 */
public enum EnumOrganizationType implements IEnum {

  /**
   * 集团
   */
  GROUP,

  /**
   * 子公司
   */
  COMPANY,

  /**
   * 门店
   */
  STORE,

  /**
   * 部门
   */
  DEPART;

  @Override
  public Serializable getValue() {
    return name();
  }

}
