/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-dao
 * 文件名：	UserPO.java
 * 模块说明：
 * 修改历史：
 * 2018年2月2日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.dao.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.gomore.experiment.commons.dao.po.StandardEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户
 *
 * @author Debenson
 * @since 0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "nr_sys_user")
public class UserPO extends StandardEntity {
  private static final long serialVersionUID = 2175085935732369822L;
  
  /**
   * 根组织的管理员ID
   */
  public static final Long ROOT_USER_ID = 0L;
  public static final String ROOT_USER_NAME = "root";
  public static final String ROOT_USER_REAL_NAME = "管理员";
  public static final String ROOT_USER_PASSWORD = "gomore123";

  /**
   * 账户
   */
  private String username;

  /**
   * 密码
   */
  private String password;

  /**
   * 是否被禁用
   */
  private Boolean disabled;

  /**
   * 手机号
   */
  private String mobile;

  /**
   * 生成密码的盐
   */
  private String salt;

  /**
   * 真实姓名
   */
  private String realName;

  /**
   * 性别
   */
  private EnumGender gender;

  /**
   * 生日，格式yyyy-MM-dd
   */
  private String birthday;

  /**
   * 身份证号
   */
  private String idNo;

  /**
   * 邮箱
   */
  private String email;

  /**
   * 头像URL
   */
  private String avatar;
  
  /**
   * 工号 
   */
  private String workNumber;

}
