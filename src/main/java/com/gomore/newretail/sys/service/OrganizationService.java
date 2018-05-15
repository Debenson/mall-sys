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
import java.util.Set;

import com.gomore.experiment.commons.rest.PageResult;
import com.gomore.newretail.sys.bean.dto.organization.OrganizationCountDTO;
import com.gomore.newretail.sys.bean.dto.organization.OrganizationDTO;
import com.gomore.newretail.sys.bean.dto.organization.OrganizationPageFilter;
import com.gomore.newretail.sys.bean.dto.role.RoleDTO;
import com.gomore.newretail.sys.dao.po.OrganizationPO;

/**
 * 组织服务接口
 *
 * @author Debenson
 * @since 0.1
 */
public interface OrganizationService {

  /**
   * 新增组织
   *
   * @param org
   *          组织，非空
   * @return
   */
  Long create(OrganizationDTO org);

  /**
   * 更新组织
   *
   * @param org
   *          组织的ID不能为空，只更新非空字段
   */
  void update(OrganizationDTO org);

  /**
   * 禁用指定的组织 <br>
   * 仅当当前组织下没有启用状态的下级组织时才可以禁用当前组织。
   *
   * @param id
   *          组织ID，非空
   */
  void disable(Long id);

  /**
   * 启用指定的组织 <br>
   * 父组织启用后不会影响其下级组织的禁用启用状态。
   *
   * @param id
   *          组织ID，非空
   */
  void enable(Long id);

  /**
   * 批量查询组织
   *
   * @param filter
   *          查询条件，非空
   * @return
   */
  PageResult<OrganizationDTO> query(OrganizationPageFilter filter);

  /**
   * 查询分公司 及其下面所有子门店的数量
   * 
   * @return
   */
  List<OrganizationCountDTO> queryCount();

  /**
   * 根据ID查询组织
   *
   * @param id
   *          组织ID
   * @return 组织信息，找不到返回null
   */
  OrganizationDTO getById(Long id);

  /**
   * 取得根组织
   * 
   * @return
   */
  OrganizationDTO getRoot();

  /**
   * 取得顶级组织
   * 
   * @return
   */
  OrganizationPO getGroup();

  /**
   * 更新组织角色
   *
   * @param orgId
   *          组织ID, 非空
   * @param roleIds
   *          角色ID， 可空。空表示清空组织角色列表。
   */
  void updateOrgRoles(Long orgId, Long... roleIds);

  /**
   * 取得组织角色列表
   *
   * @param orgId
   *          组织ID，非空
   * @return 指定组织的角色列表
   */
  Set<RoleDTO> getOrgRoles(Long orgId);

}
