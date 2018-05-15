/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-service
 * 文件名：	RoleService.java
 * 模块说明：
 * 修改历史：
 * 2018年2月9日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.service;

import java.util.List;

import com.gomore.experiment.commons.rest.PageResult;
import com.gomore.newretail.sys.bean.dto.menu.MenuDTO;
import com.gomore.newretail.sys.bean.dto.role.RoleDTO;
import com.gomore.newretail.sys.bean.dto.role.RolePageFilter;

/**
 * 角色服务接口
 *
 * @author Debenson
 * @since 0.1
 */
public interface RoleService {

  /**
   * 新增角色
   *
   * @param role
   *          角色，非空
   * @return
   */
  Long create(RoleDTO role);

  /**
   * 更新角色
   *
   * @param role
   *          角色的ID不能为空，只更新非空字段
   */
  void update(RoleDTO role);

  /**
   * 根据角色ID删除指定角色
   *
   * @param ids
   *          角色ID，非空
   */
  void removeById(Long... ids);

  /**
   * 批量查询角色
   *
   * @param filter
   *          查询条件，非空
   * @return
   */
  PageResult<RoleDTO> query(RolePageFilter filter);

  /**
   * 更新角色权限
   *
   * @param roleId
   *          角色ID, 非空
   * @param permissionIds
   *          权利ID， 可空。空表示清空角色权限列表。
   */
  void updateRolePermission(Long roleId, Long... permissionIds);

  /**
   * 取得角色拥有的权限列表
   * 
   * @param roleId
   *          角色ID,非空
   * @return
   */
  List<MenuDTO> getRolePermission(Long roleId);

}
