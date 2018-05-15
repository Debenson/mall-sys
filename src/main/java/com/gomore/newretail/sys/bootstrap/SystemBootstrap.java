package com.gomore.newretail.sys.bootstrap;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gomore.experiment.commons.annotation.Tx;
import com.gomore.experiment.commons.util.JsonUtil;
import com.gomore.newretail.commons.constants.Permissions;
import com.gomore.newretail.commons.constants.Permissions.Permission;
import com.gomore.newretail.commons.util.MD5Utils;
import com.gomore.newretail.sys.bean.dto.organization.OrganizationDTO;
import com.gomore.newretail.sys.bean.dto.user.UserDTO;
import com.gomore.newretail.sys.dao.mapper.MenuMapper;
import com.gomore.newretail.sys.dao.mapper.PermissionHistoryMapper;
import com.gomore.newretail.sys.dao.po.EnumMenuType;
import com.gomore.newretail.sys.dao.po.EnumOrganizationType;
import com.gomore.newretail.sys.dao.po.MenuPO;
import com.gomore.newretail.sys.dao.po.OrganizationPO;
import com.gomore.newretail.sys.dao.po.PermissionHistoryPO;
import com.gomore.newretail.sys.dao.po.UserPO;
import com.gomore.newretail.sys.service.MenuService;
import com.gomore.newretail.sys.service.OrganizationService;
import com.gomore.newretail.sys.service.UserService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * 初始化系统组件
 * 
 * @author Debenson
 * @since 0.1
 */
@Slf4j
@Component
public class SystemBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private static final String PERMISSIONS_JSON = "permissions.json";

  @Autowired
  private PermissionHistoryMapper permHstMapper;
  @Autowired
  private MenuMapper menuMapper;
  @Autowired
  private MenuService menuService;
  @Autowired
  private UserService userService;
  @Autowired
  private OrganizationService orgService;

  @Override
  @Tx
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (event.getApplicationContext().getParent() != null) {
      return;
    }

    installRootOrganization();
    installRootUser();
    installPermissions();
  }

  /**
   * 安装顶级组织
   */
  private void installRootOrganization() {
    OrganizationDTO rootOrg = orgService.getRoot();
    if (rootOrg == null) {
      log.debug("超级组织不存在，准备创建.");
      rootOrg = new OrganizationDTO();
      rootOrg.setId(OrganizationPO.ROOT_ORG_ID);
      rootOrg.setName(OrganizationPO.ROOT_ORG_NAME);
      rootOrg.setDisabled(false);
      rootOrg.setPath("/" + OrganizationPO.ROOT_ORG_ID);
      rootOrg.setType(EnumOrganizationType.GROUP);
      orgService.create(rootOrg);
      log.debug("超级组织已经创建成功.");
    }
  }

  /**
   * 安装ROOT用户
   */
  private void installRootUser() {
    UserDTO rootUser = userService.getRootAdmin();
    if (rootUser == null) {
      log.debug("超级用户不存在，准备创建.");
      rootUser = new UserDTO();
      rootUser.setOrgId(OrganizationPO.ROOT_ORG_ID);
      rootUser.setId(UserPO.ROOT_USER_ID);
      rootUser.setUsername(UserPO.ROOT_USER_NAME);
      rootUser.setRealName(UserPO.ROOT_USER_REAL_NAME);
      rootUser.setPassword(UserPO.ROOT_USER_PASSWORD);
      userService.create(rootUser);
      log.debug("超级用户创建成功");
    }
  }

  /**
   * 安装权限
   */
  private void installPermissions() {
    log.debug("开始升级权限.");

    final String json = getPermissionContent();
    if (StringUtils.isBlank(json)) {
      log.error("权限文件内容为空，请检查{}文件", PERMISSIONS_JSON);
      return;
    }
    final List<Permission> perms = JsonUtil.toList(json, Permission.class);
    if (perms.isEmpty()) {
      log.debug("没有发现新的权限");
      return;
    }

    final String signature = MD5Utils.getMD5Code(json);
    log.debug("文件签名为: {}", signature);

    List<PermissionHistoryPO> hsts = permHstMapper.selectList(new EntityWrapper<>());
    if (hsts.isEmpty()) {
      log.debug("准备首次安装权限.");
      installPermissionsForFirst(perms);
      final PermissionHistoryPO hst = new PermissionHistoryPO();
      hst.setVersion(PermissionHistoryPO.FIRST_VERSION);
      hst.setSignature(signature);
      hst.setUpdateTime(new Date());
      permHstMapper.insert(hst);
      log.debug("权限安装完成");
    } else {
      final PermissionHistoryPO hst = hsts.get(0);
      if (Objects.equals(hst.getSignature(), signature)) {
        log.debug("权限签名一致，无需更新");
      } else {
        log.debug("权限签名发生变化， 原值={}, 新值={}，准备更新权限", hst.getSignature(), signature);
        installPermissionsForUpdate(perms);
        hst.setUpdateTime(new Date());
        permHstMapper.updateById(hst);
        log.debug("权限更新完成");
      }
    }
  }

  /**
   * 首次安装权限
   * 
   * @param permissions
   */
  private void installPermissionsForFirst(List<Permission> permissions) {
    int orderNum = 0;
    for (Permission perm : permissions) {
      installOnePermission(null, perm, orderNum++);
    }
  }

  private void installOnePermission(MenuPO parent, Permission permission, int orderNum) {
    final MenuPO menu = convertToMenu(parent, permission, orderNum);
    menuMapper.insert(menu);
    if (permission.getChildren() != null) {
      int subOrderNum = 0;
      for (Permission child : permission.getChildren()) {
        installOnePermission(menu, child, subOrderNum++);
      }
    }
  }

  private MenuPO convertToMenu(MenuPO parent, Permission permission, int orderNum) {
    MenuPO menu = new MenuPO();
    if (permission.getChildren() == null || permission.getChildren().isEmpty()) {
      menu.setType(EnumMenuType.BUTTON);
    } else {
      menu.setType(EnumMenuType.MENU);
    }
    if (parent != null) {
      menu.setParentId(parent.getId());
    }
    menu.setName(permission.getName());
    menu.setPermissions(permission.getCode());
    menu.setOrderNum(orderNum);
    return menu;
  }

  private Map<String, MenuPO> allMenusMap;

  /**
   * 更新权限
   * 
   * @param permissions
   */
  private void installPermissionsForUpdate(List<Permission> permissions) {
    // 先查出所有权限
    final List<MenuPO> allMenus = menuMapper.selectList(new EntityWrapper<>());
    allMenusMap = allMenus.stream().collect(Collectors.toMap(MenuPO::getPermissions, m -> m));

    // 插入新的权限
    int orderNum = 0;
    for (Permission perm : permissions) {
      updateOnePermission(null, perm, orderNum++);
    }

    // 删除数据库中已经无效的权限
    List<Permission> flats = Lists.newArrayList();
    for (Permission perm : permissions) {
      retrieveFlatLocalPermissions(perm, flats);
    }
    Map<String, Permission> localMenusMap = flats.stream()
        .collect(Collectors.toMap(Permission::getCode, p -> p));
    for (MenuPO menuDb : allMenus) {
      if (!localMenusMap.containsKey(menuDb.getPermissions())) {
        // 权限已经被删除
        log.debug("本地已经删除了权限 {}，即将从数据库中删除", menuDb.getPermissions());
        menuService.removeById(menuDb.getId());
      }
    }

    localMenusMap.clear();
    allMenusMap.clear();
  }

  private void retrieveFlatLocalPermissions(Permission permission, List<Permission> flats) {
    flats.add(permission);
    if (permission.getChildren() != null) {
      for (Permission perm : permission.getChildren()) {
        retrieveFlatLocalPermissions(perm, flats);
      }
    }
  }

  private void updateOnePermission(MenuPO parent, Permission permission, int orderNum) {
    MenuPO menu = allMenusMap.get(permission.getCode());
    if (menu == null) {
      menu = convertToMenu(parent, permission, orderNum);
      menuMapper.insert(menu);
    } else if (!Objects.equals(permission.getName(), menu.getName())) {
      // 修改了权限名称
      menu.setName(permission.getName());
      MenuPO updateMenu = new MenuPO();
      updateMenu.setName(permission.getName());
      updateMenu.setId(menu.getId());
      menuMapper.updateById(updateMenu);
    }

    if (permission.getChildren() != null) {
      int subOrderNum = 0;
      for (Permission child : permission.getChildren()) {
        updateOnePermission(menu, child, subOrderNum++);
      }
    }
  }

  /**
   * 取得权限文件内容
   * 
   * @return
   */
  private String getPermissionContent() {
    InputStream input = Permissions.class.getResourceAsStream(PERMISSIONS_JSON);
    try {
      return IOUtils.toString(input, "utf-8");
    } catch (IOException e) {
      log.error("", e);
      return null;
    }
  }

}
