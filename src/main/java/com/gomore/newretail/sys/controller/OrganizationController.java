/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名： newretail-sys-api
 * 文件名： OrganizationController.java
 * 模块说明：
 * 修改历史：
 * 2018年2月2日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gomore.experiment.commons.rest.JsonResponse;
import com.gomore.experiment.commons.rest.PageResult;
import com.gomore.experiment.commons.rest.annotation.JsonGetMapping;
import com.gomore.experiment.commons.rest.annotation.JsonPostMapping;
import com.gomore.experiment.commons.rest.annotation.JsonPutMapping;
import com.gomore.newretail.commons.constants.Permissions;
import com.gomore.newretail.sys.bean.dto.menu.MenuTreeDTO;
import com.gomore.newretail.sys.bean.dto.organization.CreateOrganizationDTO;
import com.gomore.newretail.sys.bean.dto.organization.OrganizationCountDTO;
import com.gomore.newretail.sys.bean.dto.organization.OrganizationDTO;
import com.gomore.newretail.sys.bean.dto.organization.OrganizationPageFilter;
import com.gomore.newretail.sys.bean.dto.organization.UpdateOrganizationDTO;
import com.gomore.newretail.sys.bean.dto.organization.UpdateOrganizationRoleDTO;
import com.gomore.newretail.sys.bean.dto.role.RoleDTO;
import com.gomore.newretail.sys.converter.CreateOrgConverter;
import com.gomore.newretail.sys.service.MenuService;
import com.gomore.newretail.sys.service.OrganizationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 组织维护接口
 *
 * @author Debenson
 * @since 0.1
 */
@RestController
@RequestMapping("/api/sys/org")
@Api(description = "组织对外接口")
public class OrganizationController {

  @Autowired
  private OrganizationService orgService;
  @Autowired
  private MenuService menuService;
  @Autowired
  private CreateOrgConverter createOrgConverter;

  /**
   * 新增组织
   *
   * @param org
   *          组织
   * @return 组织ID
   */
  @RequiresPermissions(Permissions.SYS_ORG_CREATE)
  @JsonPostMapping("/create")
  @ApiOperation(value = "新增组织")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "返回新增的组织ID") })
  public JsonResponse<Long> create(@RequestBody @NotNull @Validated @ApiParam(value = "新增组织信息",
      required = true) CreateOrganizationDTO org) {
    OrganizationDTO orgDto = createOrgConverter.toOrgDTO(org);
    Long id = orgService.create(orgDto);
    return JsonResponse.ok(id);
  }

  /**
   * 更新组织
   *
   * @param org
   *          组织
   * @return
   */
  @RequiresPermissions(Permissions.SYS_ORG_UPDATE)
  @JsonPutMapping("/update")
  @ApiOperation(value = "修改组织")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "空") })
  public JsonResponse<Void> update(@RequestBody @NotNull @Validated @ApiParam(value = "组织信息",
      required = true) UpdateOrganizationDTO org) {
    OrganizationDTO orgDto = createOrgConverter.toOrgDTO(org);
    orgService.update(orgDto);
    return JsonResponse.ok();
  }

  /**
   * 禁用组织 <br>
   * 组织不提供物理删除功能。
   *
   * @param id
   *          组织标识
   * @return
   */
  @RequiresPermissions(Permissions.SYS_ORG_DISABLE)
  @JsonPutMapping("/disable")
  @ApiOperation(value = "禁用组织")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "空") })
  public JsonResponse<Void> disable(
      @RequestParam @ApiParam(value = "组织标识", required = true) Long id) {
    orgService.disable(id);
    return JsonResponse.ok();
  }

  /**
   * 启用组织
   *
   * @param id
   *          组织标识
   * @return
   */
  @RequiresPermissions(Permissions.SYS_ORG_ENABLE)
  @PutMapping("/enable")
  @ApiOperation(value = "启用组织")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "空") })
  public JsonResponse<Void> enable(
      @RequestParam @ApiParam(value = "组织标识", required = true) Long id) {
    orgService.enable(id);
    return JsonResponse.ok();
  }

  /**
   * 分页查询组织
   *
   * @param filter
   *          筛选条件，非空
   * @throws Exception
   */
  @RequiresPermissions(Permissions.SYS_ORG_VIEW)
  @JsonGetMapping("/query")
  @ApiOperation(value = "分页查询组织")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "返回分页的组织信息") })
  public JsonResponse<PageResult<OrganizationDTO>> query(@NotNull @Validated @ApiParam(
      value = "查询条件", required = true) OrganizationPageFilter filter) {
    PageResult<OrganizationDTO> pageResult = orgService.query(filter);
    return JsonResponse.ok(pageResult);
  }

  /**
   * 查询组织及其下级组织数量
   * 
   * @throws Exception
   */
  @RequiresPermissions(Permissions.SYS_ORG_VIEW)
  @JsonGetMapping("/queryCompanyCount")
  @ApiOperation(value = "查询组织及其下级组织数量")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "返回分公司及其门店数量") })
  public JsonResponse<List<OrganizationCountDTO>> queryCount() {
    return JsonResponse.ok(orgService.queryCount());
  }

  /**
   * 修改组织角色
   *
   * @param updateOrgRole
   *          角色列表
   * @return
   */
  @RequiresPermissions(Permissions.SYS_ORG_EDIT_ROLE)
  @JsonPutMapping("/updateRoles")
  @ApiOperation(value = "修改组织角色")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "空") })
  public JsonResponse<Void> updateRoles(@RequestBody @NotNull @Validated @ApiParam(value = "组织角色",
      required = true) UpdateOrganizationRoleDTO updateOrgRole) {
    orgService.updateOrgRoles(updateOrgRole.getOrgId(), updateOrgRole.getRoleIds());
    return JsonResponse.ok();
  }

  /**
   * 取得组织角色列表
   *
   * @param orgId
   *          组织ID
   * @return 组织角色列表
   */
  @RequiresPermissions(Permissions.SYS_ORG_VIEW)
  @JsonGetMapping("/getRoles")
  @ApiOperation(value = "组织角色列表")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "组织角色列表") })
  public JsonResponse<Set<RoleDTO>> getRoles(
      @RequestParam @ApiParam(value = "组织标识", required = true) Long orgId) {
    final Set<RoleDTO> roles = orgService.getOrgRoles(orgId);
    return JsonResponse.ok(roles);
  }

  /**
   * 取组织的权限树
   *
   * @return 组织的权限树
   */
  @RequiresPermissions(Permissions.SYS_ORG_VIEW)
  @GetMapping("/getOrgMenuTree")
  @ApiOperation(value = "取得指定组织可见的权限树")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "取得指定组织可见的权限树") })
  public JsonResponse<List<MenuTreeDTO>> getOrgMenuTree(
      @RequestParam @ApiParam(value = "组织标识", required = true) Long orgId) {
    final MenuTreeDTO menuTree = menuService.getOrgMenuTree(orgId);
    return JsonResponse.ok(menuTree.getChildren());
  }

}
