/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-service
 * 文件名：	UserService.java
 * 模块说明：
 * 修改历史：
 * 2018年2月2日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.service;

import java.util.Set;

import com.gomore.experiment.commons.rest.PageResult;
import com.gomore.newretail.sys.bean.dto.role.RoleDTO;
import com.gomore.newretail.sys.bean.dto.user.UserDTO;
import com.gomore.newretail.sys.bean.dto.user.UserPageFilter;

/**
 * 系统用户服务接口
 *
 * @author Debenson
 * @since 0.1
 */
public interface UserService {

  /**
   * 新增用户
   *
   * @param user
   *          用户，非空
   * @return
   */
  Long create(UserDTO user);

  /**
   * 更新用户
   *
   * @param user
   *          用户的ID不能为空，只更新非空字段
   */
  void update(UserDTO user);

  /**
   * 通过ID查询用户
   *
   * @param id
   *          用户标识，非空
   * @return 找不到返回 null
   */
  UserDTO getById(Long id);

  /**
   * 取得根组织的管理员账户
   * 
   * @return
   */
  UserDTO getRootAdmin();

  /**
   * 批量查询用户
   *
   * @param filter
   *          查询条件，非空
   * @return
   */
  PageResult<UserDTO> query(UserPageFilter filter);

  /**
   * 通过账户名称查询用户
   *
   * @param username
   *          账户名称
   * @return 找不到返回 null
   */
  UserDTO getByUserName(String username);

  /**
   * 根据用户ID删除用户
   *
   * @param id
   *          用户ID，非空
   * @return 成功删除的记录数
   */
  int deleteById(Long id);

  /**
   * 禁用用户
   * 
   * @param id
   *          用户ID，非空
   */
  void disable(Long id);

  /**
   * 启用用户
   * 
   * @param id
   *          用户ID，非空
   */
  void enable(Long id);

  /**
   * 更新用户角色
   *
   * @param userId
   *          用户ID, 非空
   * @param workingOrgId
   *          工作组织ID，可空，为空时等于用户的所属组织
   * @param roleIds
   *          角色ID， 可空。空表示清空用户角色列表。
   */
  void updateUserRoles(Long userId, Long workingOrgId, Long... roleIds);

  /**
   * 取得用户角色列表
   * 
   * @param userId
   *          用户ID，非空
   * @param workingOrgId
   *          工作组织ID，可空，为空时等于用户的所属组织
   * @return 指定用户的角色列表
   */
  Set<RoleDTO> getUserRoles(Long userId, Long workingOrgId);

  /**
   * 重置密码
   * 
   * @param userId
   *          用户ID，非空
   * @param oldPassword
   *          原密码，如果原密码为空，可传null
   * @param newPassword
   *          新密码，非空
   */
  void modifyPassword(Long userId, String oldPassword, String newPassword);

  /**
   * 重置密码，密码被重置为默认密码123456
   * 
   * @param userId
   *          用户ID，非空
   */
  void resetPassword(Long userId);
}
