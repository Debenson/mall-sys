/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-service
 * 文件名：	RoleServiceImpl.java
 * 模块说明：
 * 修改历史：
 * 2018年2月11日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.gomore.experiment.commons.exception.ServiceException;
import com.gomore.experiment.commons.rest.PageResult;
import com.gomore.newretail.commons.exception.MyExceptionCode;
import com.gomore.newretail.sys.bean.dto.menu.MenuDTO;
import com.gomore.newretail.sys.bean.dto.role.RoleDTO;
import com.gomore.newretail.sys.bean.dto.role.RolePageFilter;
import com.gomore.newretail.sys.converter.RoleConverter;
import com.gomore.newretail.sys.dao.mapper.MenuMapper;
import com.gomore.newretail.sys.dao.mapper.RoleMapper;
import com.gomore.newretail.sys.dao.po.MenuPO;
import com.gomore.newretail.sys.dao.po.RolePO;
import com.gomore.newretail.sys.dao.po.RolePermissionRPO;
import com.gomore.newretail.sys.service.RoleService;

import io.jsonwebtoken.lang.Assert;

/**
 * @author Debenson
 * @since 0.1
 */
@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleMapper roleMapper;
  @Autowired
  private MenuMapper menuMapper;
  @Autowired
  private RoleConverter roleConverter;

  @Override
  public Long create(RoleDTO role) {
    Assert.notNull(role, "角色不能为空");
    Assert.notNull(role.getOrgId(), "角色所属组织不能为空");

    final RolePO po = roleConverter.toOrg(role);
    roleMapper.insert(po);
    return po.getId();
  }

  @Override
  public void update(RoleDTO role) {
    Assert.notNull(role, "角色不能为空");
    Assert.notNull(role.getId(), "角色标识不能为空");

    int count = roleMapper.selectCount(new EntityWrapper<RolePO>().eq("id", role.getId()));
    if (count <= 0) {
      throw new ServiceException(MyExceptionCode.roleNotFound);
    }

    final RolePO po = roleConverter.toOrg(role);
    roleMapper.updateById(po);
  }

  @Override
  public void removeById(Long... ids) {
    Assert.notEmpty(ids, "角色标识不能为空");

    roleMapper.delete(new EntityWrapper<RolePO>().in("id", ids));
  }

  @Override
  public PageResult<RoleDTO> query(RolePageFilter filter) {
    org.springframework.util.Assert.notNull(filter, "查询条件不能为空");

    final Pagination page = filter.toPagination();
    final List<RoleDTO> dtos = roleMapper.query(page, filter);
    return new PageResult<>(page, dtos);
  }

  @Override
  public void updateRolePermission(Long roleId, Long... permissionIds) {
    Assert.notNull(roleId, "角色ID不能为空");

    // 先删除所有权限
    new RolePermissionRPO().delete(new EntityWrapper<RolePermissionRPO>().eq("role_id", roleId));

    // 再插入新权限
    if (permissionIds != null) {
      for (Long permId : permissionIds) {
        int count = menuMapper.selectCount(new EntityWrapper<MenuPO>().eq("id", permId));
        if (count <= 0) {
          throw new ServiceException(MyExceptionCode.menuNotFound);
        }
        RolePermissionRPO relation = new RolePermissionRPO();
        relation.setRoleId(roleId);
        relation.setPermissionId(permId);
        relation.insert();
      }
    }
  }

  @Override
  public List<MenuDTO> getRolePermission(Long roleId) {
    Assert.notNull(roleId, "角色ID不能为空");
    return menuMapper.getRoleMenus(roleId);
  }

}
