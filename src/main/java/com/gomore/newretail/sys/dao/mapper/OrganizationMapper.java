package com.gomore.newretail.sys.dao.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.gomore.newretail.sys.bean.dto.organization.OrganizationPageFilter;
import com.gomore.newretail.sys.bean.dto.role.RoleDTO;
import com.gomore.newretail.sys.dao.po.OrganizationPO;

/**
 * 组织数据访问对象
 *
 * @author Debenson
 * @since 0.1
 */
public interface OrganizationMapper extends BaseMapper<OrganizationPO> {

  /**
   * 取得组织的所有角色
   *
   * @param orgId
   *          组织ID，非空
   * @return
   */
  Set<RoleDTO> getAllRoles(Long orgId);

  /**
   * 取得顶级组织
   * 
   * @return
   */
  OrganizationPO getGroup();

  /**
   * 组织筛选
   * 
   * @param page
   *          分页条件
   * @param filter
   *          筛选条件
   * @return
   */
  List<OrganizationPO> query(Pagination page, @Param("filter") OrganizationPageFilter filter);

}