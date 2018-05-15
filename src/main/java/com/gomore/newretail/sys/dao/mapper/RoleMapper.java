package com.gomore.newretail.sys.dao.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.gomore.newretail.sys.bean.dto.role.RoleDTO;
import com.gomore.newretail.sys.bean.dto.role.RolePageFilter;
import com.gomore.newretail.sys.dao.po.RolePO;

/**
 * 角色数据访问对象
 *
 * @author Debenson
 * @since 0.1
 */
public interface RoleMapper extends BaseMapper<RolePO> {

  /**
   * 取得用户拥有的角色。
   *
   * @param userId
   *          用户ID，非空
   * @param workingOrgId
   *          当前用户的工作组织，非空
   * @return 角色ID的列表
   */
  Set<RoleDTO> getUserRoles(@Param("userId") Long userId, @Param("workingOrgId") Long workingOrgId);

  /**
   * 角色筛选
   * 
   * @param page
   *          分页条件
   * @param filter
   *          筛选条件
   * @return
   */
  List<RoleDTO> query(Pagination page, @Param("filter") RolePageFilter filter);
}