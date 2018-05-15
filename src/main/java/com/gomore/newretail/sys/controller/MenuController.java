/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名： newretail-sys
 * 文件名： MenuController.java
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
import com.gomore.experiment.commons.rest.annotation.JsonDeleteMapping;
import com.gomore.experiment.commons.rest.annotation.JsonPostMapping;
import com.gomore.experiment.commons.rest.annotation.JsonPutMapping;
import com.gomore.newretail.commons.constants.Permissions;
import com.gomore.newretail.sys.bean.dto.menu.CreateMenuDTO;
import com.gomore.newretail.sys.bean.dto.menu.MenuDTO;
import com.gomore.newretail.sys.bean.dto.menu.UpdateMenuDTO;
import com.gomore.newretail.sys.converter.CreateMenuConverter;
import com.gomore.newretail.sys.service.MenuService;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 系统菜单维护接口
 *
 * @author Debenson
 * @since 0.1
 */
@RestController
@RequestMapping("/api/sys/menu")
@Api(description = "系统菜单对外接口")
public class MenuController {

  @Autowired
  private MenuService menuService;
  @Autowired
  private CreateMenuConverter insertMenuConverter;

  /**
   * 新增菜单
   *
   * @param menu
   *          菜单
   * @return 菜单ID
   */
  @RequiresPermissions(Permissions.SYS_MENU_CREATE)
  @JsonPostMapping("/create")
  @ApiOperation(value = "新增菜单")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "返回新增的菜单ID") })
  public JsonResponse<Long> create(@RequestBody @NotNull @Validated @ApiParam(value = "菜单信息",
      required = true) CreateMenuDTO menu) {
    MenuDTO menuDto = insertMenuConverter.toMenuDTO(menu);
    Long id = menuService.create(menuDto);
    return JsonResponse.ok(id);
  }

  /**
   * 更新菜单
   *
   * @param menu
   *          菜单
   * @return
   */
  @RequiresPermissions(Permissions.SYS_MENU_UPDATE)
  @JsonPutMapping("/update")
  @ApiOperation(value = "修改菜单")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "空") })
  public JsonResponse<Void> update(@RequestBody @NotNull @Validated @ApiParam(value = "菜单信息",
      required = true) UpdateMenuDTO menu) {
    MenuDTO menuDto = insertMenuConverter.toMenuDTO(menu);
    menuService.update(menuDto);
    return JsonResponse.ok();
  }

  /**
   * 删除菜单
   *
   * @param ids
   *          菜单标识，多个值以英文逗号分隔“,”
   * @return
   */
  @RequiresPermissions(Permissions.SYS_MENU_REMOVE)
  @JsonDeleteMapping("/remove")
  @ApiOperation(value = "删除菜单")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "空") })
  public JsonResponse<Void> remove(
      @RequestParam @ApiParam(value = "菜单标识，多个值以英文逗号分隔“,”", required = true) String ids) {
    List<Long> menuIds = Lists.newArrayList(ids.split(",")).stream()
        .map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
    menuService.removeById(menuIds.toArray(new Long[0]));
    return JsonResponse.ok();
  }

}
