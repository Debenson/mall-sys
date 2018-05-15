/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名： newretail-sys-api
 * 文件名： RoleController.java
 * 模块说明：
 * 修改历史：
 * 2018年2月2日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gomore.experiment.commons.rest.JsonResponse;
import com.gomore.experiment.commons.rest.PageResult;
import com.gomore.experiment.commons.rest.annotation.JsonDeleteMapping;
import com.gomore.experiment.commons.rest.annotation.JsonGetMapping;
import com.gomore.experiment.commons.rest.annotation.JsonPostMapping;
import com.gomore.experiment.commons.rest.annotation.JsonPutMapping;
import com.gomore.newretail.commons.auth.AuthorizedUser;
import com.gomore.newretail.commons.auth.AuthorizedUserHolder;
import com.gomore.newretail.commons.constants.Permissions;
import com.gomore.newretail.sys.bean.dto.menu.MenuDTO;
import com.gomore.newretail.sys.bean.dto.role.CreateRoleDTO;
import com.gomore.newretail.sys.bean.dto.role.RoleDTO;
import com.gomore.newretail.sys.bean.dto.role.RolePageFilter;
import com.gomore.newretail.sys.bean.dto.role.UpdateRoleDTO;
import com.gomore.newretail.sys.bean.dto.role.UpdateRolePermissionDTO;
import com.gomore.newretail.sys.converter.CreateRoleConverter;
import com.gomore.newretail.sys.dao.po.OrganizationPO;
import com.gomore.newretail.sys.service.RoleService;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 角色维护接口
 *
 * @author Debenson
 * @since 0.1
 */
@RestController
@RequestMapping("/api/sys/role")
@Api(description = "系统角色对外接口")
public class RoleController {

  @Autowired
  private RoleService roleService;
  @Autowired
  private CreateRoleConverter insertRoleConverter;

  /**
   * 新增角色
   *
   * @param role
   *          角色
   * @return 角色ID
   */
  @RequiresPermissions(Permissions.SYS_ROLE_CREATE)
  @JsonPostMapping("/create")
  @ApiOperation(value = "新增角色")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "返回新增的角色ID") })
  public JsonResponse<Long> create(@RequestBody @NotNull @Validated @ApiParam(value = "角色信息",
      required = true) CreateRoleDTO role) {
    RoleDTO menuDto = insertRoleConverter.toRoleDTO(role);
    Long id = roleService.create(menuDto);
    return JsonResponse.ok(id);
  }

  /**
   * 更新角色
   *
   * @param role
   *          角色
   * @return
   */
  @RequiresPermissions(Permissions.SYS_ROLE_UPDATE)
  @JsonPutMapping("/update")
  @ApiOperation(value = "修改角色")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "空") })
  public JsonResponse<Void> update(@RequestBody @NotNull @Validated @ApiParam(value = "角色信息",
      required = true) UpdateRoleDTO role) {
    RoleDTO roleDto = insertRoleConverter.toRoleDTO(role);
    roleService.update(roleDto);
    return JsonResponse.ok();
  }

  /**
   * 删除角色
   *
   * @param ids
   *          角色标识，多个值以英文逗号分隔“,”
   * @return
   */
  @RequiresPermissions(Permissions.SYS_ROLE_REMOVE)
  @JsonDeleteMapping("/remove")
  @ApiOperation(value = "删除角色")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "空") })
  public JsonResponse<Void> remove(
      @RequestParam @ApiParam(value = "角色标识，多个值以英文逗号分隔“,”", required = true) String ids) {
    List<Long> menuIds = Lists.newArrayList(ids.split(",")).stream()
        .map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
    roleService.removeById(menuIds.toArray(new Long[0]));
    return JsonResponse.ok();
  }

  /**
   * 分页查询角色
   *
   * @param filter
   *          筛选条件，非空
   * @throws Exception
   */
  @RequiresPermissions(Permissions.SYS_ROLE_VIEW)
  @JsonGetMapping("/query")
  @ApiOperation(value = "分页查询角色")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "返回分页的角色信息") })
  public JsonResponse<PageResult<RoleDTO>> query(
      @NotNull @Validated @ApiParam(value = "查询条件", required = true) RolePageFilter filter)
      throws Exception {
    final AuthorizedUser curUser = AuthorizedUserHolder.getUser();
    if (curUser != null && curUser.getWorkingOrg() != null
        && !OrganizationPO.ROOT_ORG_ID.equals(curUser.getWorkingOrgId())) {
      // root组织也可以看到所有角色
      filter.setUpperOrgPathStartWith(curUser.getWorkingOrg().getPath());
    }
    PageResult<RoleDTO> pageResult = roleService.query(filter);
    return JsonResponse.ok(pageResult);
  }

  /**
   * 修改角色权限
   *
   * @param request
   *          修改角色权限请求，非空
   * @return
   */
  @RequiresPermissions(Permissions.SYS_ROLE_EDIT_PERMISSION)
  @JsonPutMapping("/updatePermission")
  @ApiOperation(value = "修改角色权限")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "空") })
  public JsonResponse<Void> updatePermission(@RequestBody @NotNull @Validated @ApiParam(
      value = "角色权限", required = true) UpdateRolePermissionDTO request) {
    roleService.updateRolePermission(request.getRoleId(), request.getPermissionIds());
    return JsonResponse.ok();
  }

  /**
   * 取得角色拥有的权限
   *
   * @param request
   *          修改角色权限请求，非空
   * @return
   */
  @RequiresPermissions(Permissions.SYS_ROLE_VIEW)
  @JsonGetMapping("/getPermission")
  @ApiOperation(value = "取得角色拥有的权限")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "角色拥有的权限集合") })
  public JsonResponse<List<MenuDTO>> getPermission(
      @RequestParam @ApiParam(value = "角色ID", required = true) Long roleId) {
    List<MenuDTO> perms = roleService.getRolePermission(roleId);
    return JsonResponse.ok(perms);
  }

}
