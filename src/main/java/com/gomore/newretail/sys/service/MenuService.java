/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-service
 * 文件名：	MenuService.java
 * 模块说明：
 * 修改历史：
 * 2018年2月9日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.service;

import java.util.Set;

import com.gomore.newretail.sys.bean.dto.menu.MenuDTO;
import com.gomore.newretail.sys.bean.dto.menu.MenuTreeDTO;

/**
 * 权限服务接口
 *
 * @author Debenson
 * @since 0.1
 */
public interface MenuService {

  /**
   * 新增菜单
   *
   * @param menu
   *          菜单，非空
   * @return
   */
  Long create(MenuDTO menu);

  /**
   * 更新菜单
   *
   * @param menu
   *          菜单的ID不能为空，只更新非空字段
   */
  void update(MenuDTO menu);

  /**
   * 根据菜单ID删除指定菜单
   *
   * @param ids
   *          菜单ID，非空
   */
  void removeById(Long... ids);

  /**
   * 取得组织的菜单树
   *
   * @param orgId
   *          所属组织ID，非空
   * @return 权限树
   */
  MenuTreeDTO getOrgMenuTree(Long orgId);

  /**
   * 取得指定用户拥有的菜单树
   * 
   * @param userId
   *          用户ID，非空。
   * @param workingOrgId
   *          工作组织ID，非空。
   * @return 权限树
   */
  MenuTreeDTO getUserMenuTree(Long userId, Long workingOrgId);

  /**
   * 取得指定用户的权限列表
   *
   * @param userId
   *          用户ID，非空
   * @param workingOrgId
   *          工作组织ID，非空。
   * @return 权限集合
   * @see MenuDTO#getPermissions()
   */
  Set<String> getUserPermission(Long userId, Long workingOrgId);

}
