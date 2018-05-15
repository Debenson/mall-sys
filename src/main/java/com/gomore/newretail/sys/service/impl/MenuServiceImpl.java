/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-service
 * 文件名：	MenuServiceImpl.java
 * 模块说明：
 * 修改历史：
 * 2018年2月9日 - Debenson - 创建。
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
import com.gomore.experiment.commons.exception.ServiceException;
import com.gomore.newretail.commons.exception.MyExceptionCode;
import com.gomore.newretail.sys.bean.dto.menu.MenuDTO;
import com.gomore.newretail.sys.bean.dto.menu.MenuTreeDTO;
import com.gomore.newretail.sys.converter.MenuConverter;
import com.gomore.newretail.sys.dao.mapper.MenuMapper;
import com.gomore.newretail.sys.dao.mapper.OrganizationMapper;
import com.gomore.newretail.sys.dao.po.EnumMenuType;
import com.gomore.newretail.sys.dao.po.MenuPO;
import com.gomore.newretail.sys.dao.po.OrganizationPO;
import com.gomore.newretail.sys.service.MenuService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 菜单服务实现
 *
 * @author Debenson
 * @since 0.1
 */
@Service
public class MenuServiceImpl implements MenuService {

  @Autowired
  private MenuConverter menuConverter;
  @Autowired
  private MenuMapper menuMapper;
  @Autowired
  private OrganizationMapper orgMapper;

  @Override
  public Long create(MenuDTO menu) {
    // 自定义校验
    if (EnumMenuType.MENU.equals(menu.getType()) && StringUtils.isBlank(menu.getUrl())) {
      throw new IllegalArgumentException("菜单地址不能为空");
    }
    // 检查父菜单是否存在
    if (menu.getParentId() != null && !isExists(menu.getParentId())) {
      throw new ServiceException(MyExceptionCode.parentMenuNotFound);
    }

    final MenuPO po = menuConverter.toMenu(menu);
    menuMapper.insert(po);
    return po.getId();
  }

  @Override
  public void update(MenuDTO menu) {
    Assert.notNull(menu, "菜单不能为空");
    Assert.notNull(menu.getId(), "菜单的ID不能为空");

    if (!isExists(menu.getId())) {
      throw new ServiceException(MyExceptionCode.menuNotFound);
    }

    // 自定义校验
    if (EnumMenuType.MENU.equals(menu.getType()) && StringUtils.isBlank(menu.getUrl())) {
      throw new IllegalArgumentException("菜单地址不能为空");
    }
    final MenuPO po = menuConverter.toMenu(menu);
    menuMapper.updateById(po);
  }

  @Override
  public void removeById(Long... ids) {
    Assert.notEmpty(ids, "菜单id不能为空");

    // 先删除菜单与角色之间的关系
    menuMapper.deleteRoleMenus(ids);
    // 再删除菜单本身
    menuMapper.deleteBatchIds(Lists.newArrayList(ids));
  }

  @Override
  public Set<String> getUserPermission(Long userId, Long workingOrgId) {
    Assert.notNull(userId, "用户ID不能为空");
    Assert.notNull(workingOrgId, "用户的工作组织不能为空");

    // 去重
    final Set<String> permList = menuMapper.getUserPermissions(userId, workingOrgId);
    final Set<String> filteredPerms = Sets.newHashSet();
    for (String perms : permList) {
      if (StringUtils.isNotBlank(perms)) {
        filteredPerms.addAll(Lists.newArrayList(perms.split(",")).stream().map(item -> item.trim())
            .collect(Collectors.toList()));
      }
    }
    return filteredPerms;
  }

  @Override
  public MenuTreeDTO getOrgMenuTree(Long orgId) {
    Assert.notNull(orgId, "组织ID不能为空");
    // TODO 先不过滤当前用户的权限

    OrganizationPO org = orgMapper.selectById(orgId);
    if (org == null) {
      throw new ServiceException(MyExceptionCode.orgNotFound);
    }

    List<MenuDTO> menus = null;
    if (org.getOrgId() == null) {
      // 顶级组织，取全部权限
      List<MenuPO> pos = menuMapper.selectList(new EntityWrapper<MenuPO>().orderBy("order_num"));
      menus = menuConverter.toMenuDTOs(pos);
    } else {
      menus = menuMapper.getOrgMenus(orgId);
    }
    final MenuTreeDTO root = MenuTreeDTO.buildTree(menus);
    return root;
  }

  @Override
  public MenuTreeDTO getUserMenuTree(Long userId, Long workingOrgId) {
    Assert.notNull(userId, "用户ID不能为空");
    Assert.notNull(workingOrgId, "用户的工作组织不能为空");

    final List<MenuDTO> menus = menuMapper.getUserMenus(userId, workingOrgId);
    final MenuTreeDTO root = MenuTreeDTO.buildTree(menus);
    return root;
  }

  /**
   * 判断指定标识的菜单是否存在
   *
   * @param id
   * @return
   */
  private boolean isExists(Long id) {
    Assert.notNull(id, "菜单ID不能为空");

    int count = menuMapper.selectCount(new EntityWrapper<MenuPO>().eq("id", id));
    return count > 0;
  }

}
