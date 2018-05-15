/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-api
 * 文件名：	AppController.java
 * 模块说明：
 * 修改历史：
 * 2018年3月5日 - liyang - 创建。
 */
package com.gomore.newretail.sys.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gomore.experiment.commons.rest.JsonResponse;
import com.gomore.experiment.commons.rest.annotation.JsonGetMapping;
import com.gomore.experiment.commons.rest.annotation.JsonPostMapping;
import com.gomore.experiment.commons.rest.annotation.JsonPutMapping;
import com.gomore.newretail.commons.constants.Permissions;
import com.gomore.newretail.sys.bean.dto.app.AppVersionDTO;
import com.gomore.newretail.sys.bean.dto.app.CreateAppVersionDTO;
import com.gomore.newretail.sys.bean.dto.app.UpdateAppVersionDTO;
import com.gomore.newretail.sys.converter.CreateAppVersionConvert;
import com.gomore.newretail.sys.dao.po.EnumAppType;
import com.gomore.newretail.sys.service.AppService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * APP版本管理
 *
 * @author liyang
 * @since 0.1
 */
@RestController
@RequestMapping("/api/sys/app")
@Api(description = "App对外接口")
public class AppController {
  @Autowired
  private AppService appService;

  @Autowired
  CreateAppVersionConvert createAppVersionConvert;

  /**
   * 取得App最新版本信息
   * 
   * @param type
   * @param version
   * @param osVersion
   * @return 前服务器App最新版本信息
   * @throws Exception
   */
  @JsonGetMapping("/version/getLastest")
  @ApiOperation(value = "取得App最新版本信息")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "返回查询类型下的最新APP版本信息") })
  public JsonResponse<AppVersionDTO> getLastestVersion(
      @RequestParam(required = true) @Valid @ApiParam(value = "安装包类型",
          required = true) EnumAppType type,
      @RequestParam(required = true) @Valid @ApiParam(
          value = "客户端版本号,约定规则为major(1位)+minor(2位)+point(3位) 例如:100000",
          required = true) Long version,
      @RequestParam @ApiParam(value = "客户端操作系统版本", required = false,
          allowableValues = "range[0,50]") String osVersion)
      throws Exception {
    AppVersionDTO ipapk = appService.getLastestVersion(type, version, osVersion);
    return JsonResponse.ok(ipapk);
  }

  /**
   * 新增版本
   *
   * @param version
   *          版本
   * @return 版本id
   */
  @RequiresPermissions(Permissions.SYS_APP_CREATE)
  @JsonPostMapping("/version/create")
  @ApiOperation(value = "新增版本")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "返回新增的版本ID") })
  public JsonResponse<Long> create(@RequestBody @NotNull @Validated @ApiParam(value = "版本信息",
      required = true) CreateAppVersionDTO version) {

    AppVersionDTO appVersionDTO = createAppVersionConvert.toAppVersionDTO(version);
    Long id = appService.createAppVersion(appVersionDTO);
    return JsonResponse.ok(id);
  }

  /**
   * 更新版本
   *
   * @param version
   *          版本信息
   * @return
   */
  @RequiresPermissions(Permissions.SYS_APP_UPDATE)
  @JsonPutMapping("/version/update")
  @ApiOperation(value = "更新版本")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "空") })
  public JsonResponse<Void> update(@RequestBody @NotNull @Validated @ApiParam(value = "版本信息",
      required = true) UpdateAppVersionDTO version) {
    AppVersionDTO appVersionDTO = createAppVersionConvert.toAppVersionDTO(version);
    appService.updateAppVersion(appVersionDTO);
    return JsonResponse.ok();
  }

}
