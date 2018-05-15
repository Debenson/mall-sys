package com.gomore.newretail.sys.dao.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gomore.newretail.sys.bean.dto.menu.MenuDTO;
import com.gomore.newretail.sys.dao.po.MenuPO;

/**
 * 系统菜单数据访问对象
 *
 * @author Debenson
 * @since 0.1
 */
public interface MenuMapper extends BaseMapper<MenuPO> {

  /**
   * 删除所有角色的指定菜单列表
   *
   * @param permissionIds
   *          菜单ID列表
   */
  public void deleteRoleMenus(@Param("permissionIds") Long... permissionIds);

  /**
   * 取得角色拥有的菜单列表
   * 
   * @param roleId
   *          角色ID，非空
   * @return 结构以树型排列
   */
  List<MenuDTO> getRoleMenus(@Param("roleId") Long roleId);

  /**
   * 取得用户的所有菜单
   *
   * @param userId
   *          用户ID，非空
   * @param workingOrgId
   *          工作组织ID，非空
   * @return
   */
  public List<MenuDTO> getUserMenus(@Param("userId") Long userId,
      @Param("workingOrgId") Long workingOrgId);

  /**
   * 取得用户的所有权限集合
   *
   * @param userId
   *          用户ID，非空
   * @param workingOrgId
   *          工作组织ID，非空
   * @return
   */
  public Set<String> getUserPermissions(@Param("userId") Long userId,
      @Param("workingOrgId") Long workingOrgId);

  /**
   * 取得指定组织的所有菜单集合
   *
   * @param orgId
   *          组织ID，非空
   * @return 菜单列表，结果未去重
   */
  List<MenuDTO> getOrgMenus(@Param("orgId") Long orgId);

}