/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-service
 * 文件名：	OrganizationServiceImpl.java
 * 模块说明：
 * 修改历史：
 * 2018年2月11日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.gomore.experiment.commons.exception.ServiceException;
import com.gomore.experiment.commons.rest.PageResult;
import com.gomore.newretail.commons.exception.MyExceptionCode;
import com.gomore.newretail.sys.bean.dto.organization.OrganizationCountDTO;
import com.gomore.newretail.sys.bean.dto.organization.OrganizationDTO;
import com.gomore.newretail.sys.bean.dto.organization.OrganizationPageFilter;
import com.gomore.newretail.sys.bean.dto.role.RoleDTO;
import com.gomore.newretail.sys.converter.OrganizationConverter;
import com.gomore.newretail.sys.dao.mapper.OrganizationMapper;
import com.gomore.newretail.sys.dao.mapper.RoleMapper;
import com.gomore.newretail.sys.dao.po.EnumOrganizationType;
import com.gomore.newretail.sys.dao.po.OrganizationPO;
import com.gomore.newretail.sys.dao.po.OrganizationRoleRPO;
import com.gomore.newretail.sys.dao.po.RolePO;
import com.gomore.newretail.sys.service.OrganizationService;

/**
 * @author Debenson
 * @since 0.1
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

  @Autowired
  private OrganizationMapper orgMapper;
  @Autowired
  private RoleMapper roleMapper;
  @Autowired
  private OrganizationConverter orgConverter;

  @Override
  public Long create(OrganizationDTO org) {
    Assert.notNull(org, "组织不能为空");
    Assert.hasText(org.getCode(), "组织代码不能为空");
    Assert.notNull(org.getType(), "组织类型不能为空");

    String upperOrgPath = "";
    if (org.getOrgId() != null) {
      // 如果是下级机构，则必须校验上级组织
      final OrganizationPO upperOrg = orgMapper.selectById(org.getOrgId());
      if (upperOrg == null) {
        throw new ServiceException(MyExceptionCode.upperOrgNotFound);
      }
      if (upperOrg.getDisabled() != null && upperOrg.getDisabled().booleanValue()) {
        throw new ServiceException(MyExceptionCode.upperOrgIsDisabled);
      }
      upperOrgPath = upperOrg.getPath();
    }

    // 检查code是否重复
    if (isCodeExists(org.getCode(), null)) {
      throw new ServiceException(MyExceptionCode.orgCodeExists);
    }

    // 提交设置id并重置PATH
    if (org.getId() == null) {
      org.setId(IdWorker.getId());
    }
    org.setPath(StringUtils.trimToEmpty(upperOrgPath) + "/" + org.getId());

    OrganizationPO po = orgConverter.toOrg(org);
    po.setDisabled(false);
    orgMapper.insert(po);
    return po.getId();
  }

  @Override
  public void update(OrganizationDTO org) {
    Assert.notNull(org, "组织不能为空");
    Assert.notNull(org.getId(), "组织ID不能为空");

    if (StringUtils.isNotBlank(org.getCode())) {
      // 如果要修改代码，则需要判断组织代码不能重复
      if (isCodeExists(org.getCode(), org.getId())) {
        throw new ServiceException(MyExceptionCode.orgCodeExists);
      }
    }

    OrganizationPO po = orgConverter.toOrg(org);
    // 组织路径不允许修改
    po.setPath(null);
    orgMapper.updateById(po);
  }

  @Override
  public void disable(Long id) {
    Assert.notNull(id, "组织ID不能为空");

    final OrganizationPO po = orgMapper.selectById(id);
    if (po == null) {
      throw new ServiceException(MyExceptionCode.orgNotFound);
    }
    if (po.getDisabled() != null && po.getDisabled()) {
      return;
    }

    int enabledChildrenCount = orgMapper.selectCount(
        new EntityWrapper<OrganizationPO>().eq("org_id", id).eq("disabled", Boolean.FALSE));
    if (enabledChildrenCount > 0) {
      throw new ServiceException(MyExceptionCode.orgHasEnabledLowerOrgs);
    }

    // 禁用当前组织
    OrganizationPO org = new OrganizationPO();
    org.setDisabled(true);
    orgMapper.update(org, new EntityWrapper<OrganizationPO>().eq("id", id));
  }

  @Override
  public void enable(Long id) {
    Assert.notNull(id, "组织ID不能为空");

    final OrganizationPO po = orgMapper.selectById(id);
    if (po == null) {
      throw new ServiceException(MyExceptionCode.orgNotFound);
    }
    if (po.getDisabled() == null || !po.getDisabled()) {
      return;
    }

    OrganizationPO org = new OrganizationPO();
    org.setDisabled(false);
    orgMapper.update(org, new EntityWrapper<OrganizationPO>().eq("id", id));
  }

  @Override
  public PageResult<OrganizationDTO> query(OrganizationPageFilter filter) {
    Assert.notNull(filter, "查询条件不能为空");
    if (filter.getIncludeLowerOrg() != null && filter.getIncludeLowerOrg()) {
      Assert.notNull(filter.getUpperOrgIdEquals(), "查询条件上级组织id不能为空");
    }

    final Pagination page = filter.toPagination();
    List<OrganizationPO> queryResults = orgMapper.query(page, filter);
    final List<OrganizationDTO> dtos = queryResults.stream().map(e -> orgConverter.toOrgDTO(e))
        .collect(Collectors.toList());
    return new PageResult<>(page, dtos);
  }

  @Override
  public void updateOrgRoles(Long orgId, Long... roleIds) {
    Assert.notNull(orgId, "组织ID不能为空");

    final OrganizationPO org = orgMapper.selectById(orgId);
    if (org == null) {
      throw new ServiceException(MyExceptionCode.upperOrgNotFound);
    }
    if (org.getDisabled() != null && org.getDisabled().booleanValue()) {
      throw new ServiceException(MyExceptionCode.orgIsDisabled);
    }

    // 先清空所有角色
    new OrganizationRoleRPO().delete(new EntityWrapper<OrganizationRoleRPO>().eq("org_id", orgId));

    // 再添加新的角色
    if (roleIds != null) {
      for (Long roleId : roleIds) {
        int count = roleMapper.selectCount(new EntityWrapper<RolePO>().eq("id", roleId));
        if (count <= 0) {
          throw new ServiceException(MyExceptionCode.roleNotFound);
        }

        final OrganizationRoleRPO relation = new OrganizationRoleRPO();
        relation.setOrganizationId(orgId);
        relation.setRoleId(roleId);
        relation.insert();
      }
    }
  }

  @Override
  public OrganizationDTO getById(Long id) {
    Assert.notNull(id, "组织ID不能为空");

    final OrganizationPO po = orgMapper.selectById(id);
    return orgConverter.toOrgDTO(po);
  }

  @Override
  public OrganizationDTO getRoot() {
    return getById(OrganizationPO.ROOT_ORG_ID);
  }

  @Override
  public Set<RoleDTO> getOrgRoles(Long orgId) {
    Assert.notNull(orgId, "组织ID不能为空");

    Set<RoleDTO> roles = orgMapper.getAllRoles(orgId);
    return roles;
  }

  /**
   * 组织代码是否已经存在
   *
   * @param code
   *          组织代码，必须是全局唯一
   * @param excludeId
   *          排除的组织ID，可空
   * @return
   */
  private boolean isCodeExists(String code, Long excludeId) {
    Assert.notNull(code, "组织代码不能为空");

    EntityWrapper<OrganizationPO> wrapper = new EntityWrapper<>();
    wrapper.eq("code", code);
    if (excludeId != null) {
      wrapper.ne("id", excludeId);
    }
    int count = orgMapper.selectCount(wrapper);
    return count > 0;
  }

  @Override
  public OrganizationPO getGroup() {
    final OrganizationPO po = orgMapper.getGroup();
    return po;
  }

  @Override
  public List<OrganizationCountDTO> queryCount() {
    List<OrganizationCountDTO> list = orgConverter
        .toOrganizationCountDTOs(orgMapper.selectList(new EntityWrapper<OrganizationPO>()
            .eq("type", EnumOrganizationType.COMPANY.name()).notIn("org_id", 0)));
    list.forEach(o -> {
      o.setCount(
          orgMapper.selectCount(new EntityWrapper<OrganizationPO>().eq("org_id", o.getId())));
    });
    return list;
  }
}
