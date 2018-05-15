/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-api
 * 文件名：	UserController.java
 * 模块说明：
 * 修改历史：
 * 2018年2月2日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.controller;

import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gomore.experiment.commons.exception.ServiceException;
import com.gomore.experiment.commons.rest.JsonResponse;
import com.gomore.experiment.commons.rest.PageResult;
import com.gomore.experiment.commons.rest.annotation.JsonGetMapping;
import com.gomore.experiment.commons.rest.annotation.JsonPostMapping;
import com.gomore.experiment.commons.rest.annotation.JsonPutMapping;
import com.gomore.newretail.commons.auth.AuthorizedUser;
import com.gomore.newretail.commons.auth.AuthorizedUserHolder;
import com.gomore.newretail.commons.auth.EnumUserType;
import com.gomore.newretail.commons.constants.Permissions;
import com.gomore.newretail.commons.exception.MyExceptionCode;
import com.gomore.newretail.commons.jwt.JWTCache;
import com.gomore.newretail.commons.jwt.JWTSubject;
import com.gomore.newretail.commons.jwt.JWTToken;
import com.gomore.newretail.commons.util.PasswordUtils;
import com.gomore.newretail.sys.annotation.SysLog;
import com.gomore.newretail.sys.bean.dto.menu.MenuTreeDTO;
import com.gomore.newretail.sys.bean.dto.organization.OrganizationDTO;
import com.gomore.newretail.sys.bean.dto.role.RoleDTO;
import com.gomore.newretail.sys.bean.dto.user.CreateUserDTO;
import com.gomore.newretail.sys.bean.dto.user.LoginRequest;
import com.gomore.newretail.sys.bean.dto.user.LoginResponse;
import com.gomore.newretail.sys.bean.dto.user.LoginUser;
import com.gomore.newretail.sys.bean.dto.user.ModifyPasswordDTO;
import com.gomore.newretail.sys.bean.dto.user.RefreshTokenRequest;
import com.gomore.newretail.sys.bean.dto.user.RefreshTokenResponse;
import com.gomore.newretail.sys.bean.dto.user.UpdateUserDTO;
import com.gomore.newretail.sys.bean.dto.user.UpdateUserRoleDTO;
import com.gomore.newretail.sys.bean.dto.user.UserDTO;
import com.gomore.newretail.sys.bean.dto.user.UserPageFilter;
import com.gomore.newretail.sys.converter.CreateUserConverter;
import com.gomore.newretail.sys.dao.po.EnumLogModule;
import com.gomore.newretail.sys.service.MenuService;
import com.gomore.newretail.sys.service.OrganizationService;
import com.gomore.newretail.sys.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 系统用户对外接口
 *
 * @author Debenson
 * @since 0.1
 */
@RestController
@RequestMapping("/api/sys/user")
@Api(description = "系统用户对外接口")
public class UserController {

  @Autowired
  private UserService userService;
  @Autowired
  private CreateUserConverter createUserConverter;
  @Autowired
  private OrganizationService orgService;
  @Autowired
  private MenuService menuService;
  @Autowired
  private JWTCache jwtCache;

  /**
   * 新增用户
   *
   * @param user
   *          用户，非空
   * @return
   */
  @RequiresPermissions(Permissions.SYS_USER_CREATE)
  @JsonPostMapping("/create")
  @ApiOperation(value = "新增用户")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "返回新保存的用户的ID") })
  public JsonResponse<String> create(@RequestBody @NotNull @Validated @ApiParam(value = "用户信息",
      required = true) CreateUserDTO user) {
    final OrganizationDTO ownerOrg = orgService.getById(user.getOrgId());
    Assert.notNull(ownerOrg, "找不到所属组织");
    if (!Objects.equals(user.getConfirmPassword(), user.getPassword())) {
      throw new IllegalArgumentException("密码与确认密码不一致");
    }

    UserDTO dto = createUserConverter.toUserDTO(user);
    // 默认是禁用的
    dto.setDisabled(false);
    Long id = userService.create(dto);
    return JsonResponse.ok(id.toString());
  }

  /**
   * 通过ID查询用户
   *
   * @param id
   *          用户ID，非空
   * @return
   * @throws Exception
   */
  @RequiresPermissions(Permissions.SYS_USER_VIEW)
  @JsonGetMapping("/getById")
  @ApiOperation("通过ID查询用户")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "返回用户信息，找不到将返回null") })
  @SysLog(value = "获取用户", module = EnumLogModule.other)
  public JsonResponse<UserDTO> getById(
      @RequestParam @ApiParam(value = "用户标识", required = true) Long id) throws Exception {
    UserDTO user = userService.getById(id);
    return JsonResponse.ok(user);
  }

  /**
   * 更新用户信息
   *
   * @param user
   *          用户信息
   */
  @RequiresPermissions(Permissions.SYS_USER_UPDATE)
  @JsonPutMapping("/update")
  @ApiOperation(value = "更新用户")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "空") })
  public JsonResponse<Void> update(@RequestBody @NotNull @Validated @ApiParam(value = "用户信息",
      required = true) UpdateUserDTO user) {
    Assert.notNull(user.getId(), "用户ID不能为空");

    UserDTO dto = createUserConverter.toUserDTO(user);
    userService.update(dto);
    return JsonResponse.ok();
  }

  /**
   * 分页查询用户
   *
   * @param filter
   *          筛选条件，非空
   */
  @RequiresPermissions(Permissions.SYS_USER_VIEW)
  @JsonGetMapping("/query")
  @ApiOperation(value = "分页查询用户")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "返回分页的用户信息") })
  public JsonResponse<PageResult<UserDTO>> query(
      @NotNull @Validated @ApiParam(value = "查询条件", required = true) UserPageFilter filter) {
    PageResult<UserDTO> pageResult = userService.query(filter);
    return JsonResponse.ok(pageResult);
  }

  /**
   * 根据用户标识禁用用户
   *
   * @param id
   *          用户ID，非空
   * @throws Exception
   */
  @RequiresPermissions(Permissions.SYS_USER_DISABLE)
  @JsonPutMapping("/disable")
  @ApiOperation(value = "根据用户标识禁用用户")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "空") })
  public JsonResponse<Void> disable(
      @RequestParam @ApiParam(value = "用户标识", required = true) Long id) throws Exception {
    userService.disable(id);
    return JsonResponse.ok();
  }

  /**
   * 根据用户标识启用用户
   *
   * @param id
   *          用户ID，非空
   * @throws Exception
   */
  @RequiresPermissions(Permissions.SYS_USER_ENABLE)
  @JsonPutMapping("/enable")
  @ApiOperation(value = "根据用户标识启用用户")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "空") })
  public JsonResponse<Void> enable(@RequestParam @ApiParam(value = "用户标识", required = true) Long id)
      throws Exception {
    userService.enable(id);
    return JsonResponse.ok();
  }

  /**
   * 系统用户登录
   *
   * @param request
   *          登录请求
   * @return
   * @throws Exception
   */
  @JsonPostMapping("/login")
  @ApiOperation(value = "系统用户登录")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "返回当前登录的用户和认证TOKEN") })
  public JsonResponse<LoginResponse> login(@RequestBody @ApiParam(value = "登录请求",
      required = true) @NotNull @Validated LoginRequest request) throws Exception {
    UserDTO user = userService.getByUserName(request.getUsername());
    if (user == null) {
      throw new ServiceException(MyExceptionCode.userNotFound);
    } else if (user.getDisabled() != null && user.getDisabled()) {
      throw new ServiceException(MyExceptionCode.userIsLocked);
    }

    // 校验密码
    if (!PasswordUtils.isMatch(request.getPassword(), user.getSalt(), user.getPassword())) {
      // TODO 记录失败记录
      throw new ServiceException(MyExceptionCode.usernameAndPasswordNotMatched);
    }

    // 生成JWT，并缓存
    final JWTSubject subject = new JWTSubject(EnumUserType.USER, user.getId(), user.getOrgId());
    final JWTToken tokens = jwtCache.create(subject);

    final LoginResponse loginResp = new LoginResponse();
    loginResp.setAccessToken(tokens.getToken());
    loginResp.setExpiresIn(tokens.getExpiresIn().intValue());
    loginResp.setRefreshToken(tokens.getRefreshToken());
    loginResp.setRefreshExpiresIn(tokens.getRefreshExpiresIn().intValue());
    return JsonResponse.ok(loginResp);
  }

  /**
   * 取得当前登录用户信息
   *
   * @return 当前登录用户的信息（个人信息和权限等）
   * @throws Exception
   */
  @RequiresPermissions(Permissions.SYS_USER_GET_USER_INFO)
  @JsonGetMapping("/getLoginUser")
  @ApiOperation(value = "取得当前登录用户信息（个人信息和权限等）")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "返回当前登录用户信息（个人信息和权限等）") })
  public JsonResponse<LoginUser> getLoginUser() throws Exception {
    AuthorizedUser curUser = AuthorizedUserHolder.getUser();
    final LoginUser lu = (LoginUser) curUser;
    Set<String> permsSet = menuService.getUserPermission(lu.getId(),
        lu.getWorkingOrg() == null ? null : lu.getWorkingOrg().getId());
    lu.setPermissions(permsSet);
    return JsonResponse.ok((LoginUser) curUser);
  }

  /**
   * 注销用户登录
   */
  @RequiresAuthentication
  @PostMapping("/logout")
  @ApiOperation(value = "注销用户登录")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "空") })
  public JsonResponse<Void> logout(HttpServletRequest request) {
    final Long userId = AuthorizedUserHolder.getUserId();
    if (userId != null) {
      jwtCache.removeTokens(EnumUserType.USER, userId);
    }

    try {
      SecurityUtils.getSubject().logout();
    } catch (Exception e) {
    }

    return JsonResponse.ok();
  }

  /**
   * 修改用户角色
   *
   * @param updateUserRoleRequest
   *          更新用户角色请求
   * @return
   */
  @RequiresPermissions(Permissions.SYS_USER_EDIT_ROLE)
  @JsonPutMapping("/updateRoles")
  @ApiOperation(value = "修改组织角色")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "空") })
  public JsonResponse<Void> updateRoles(@RequestBody @NotNull @Validated @ApiParam(value = "组织角色",
      required = true) UpdateUserRoleDTO updateUserRoleRequest) {
    userService.updateUserRoles(updateUserRoleRequest.getUserId(), null,
        updateUserRoleRequest.getRoleIds());
    return JsonResponse.ok();
  }

  /**
   * 取得用户角色列表
   *
   * @param id
   *          用户ID
   * @return 用户角色列表
   */
  @RequiresPermissions(Permissions.SYS_USER_VIEW)
  @JsonGetMapping("/getRoles")
  @ApiOperation(value = "用户角色列表")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "用户角色列表") })
  public JsonResponse<Set<RoleDTO>> getRoles(
      @RequestParam @ApiParam(value = "用户标识", required = true) Long id) {
    final Set<RoleDTO> roles = userService.getUserRoles(id, null);
    return JsonResponse.ok(roles);
  }

  /**
   * 取得当前用户可见的权限树
   *
   * @return 组织的权限树
   */
  @RequiresPermissions(Permissions.SYS_USER_VIEW)
  @GetMapping("/getUserMenuTree")
  @ApiOperation(value = "取得当前用户可见的权限树")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "取得当前用户可见的权限树") })
  public JsonResponse<MenuTreeDTO> getUserMenuTree() {
    final AuthorizedUser curUser = AuthorizedUserHolder.getUser();
    final MenuTreeDTO menuTree = menuService.getUserMenuTree(curUser.getId(),
        curUser.getWorkingOrg() == null ? null : curUser.getWorkingOrg().getId());
    return JsonResponse.ok(menuTree);
  }

  /**
   * 修改用户密码
   *
   * @return
   */
  @RequiresPermissions(Permissions.SYS_USER_UPDATE)
  @JsonPutMapping("/modifyPassword")
  @ApiOperation(value = "修改当前用户密码")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "修改当前用户密码") })
  public JsonResponse<Void> modifyPassword(@RequestBody @NotNull @Validated @ApiParam(
      value = "密码信息", required = true) ModifyPasswordDTO request) {
    final AuthorizedUser curUser = AuthorizedUserHolder.getUser();
    userService.modifyPassword(curUser.getId(), request.getOldPassword(), request.getNewPassword());
    return JsonResponse.ok();
  }

  /**
   * 重置用户密码
   *
   * @return
   */
  @RequiresPermissions(Permissions.SYS_USER_RESET_PWD)
  @JsonPutMapping("/resetPassword")
  @ApiOperation(value = "重置指定用户密码")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "重置指定用户密码") })
  public JsonResponse<Void> resetPassword(
      @RequestParam @ApiParam(value = "用户ID", required = true) Long id) {
    userService.resetPassword(id);
    return JsonResponse.ok();
  }

  /**
   * 刷新accessToken
   *
   * @param request
   *          刷新token请求
   * @return
   * @throws Exception
   */
  @JsonPostMapping("/refreshToken")
  @ApiOperation(value = "刷新token")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "无") })
  public JsonResponse<RefreshTokenResponse> refreshToken(@RequestBody @ApiParam(value = "刷新token请求",
      required = true) @NotNull @Validated RefreshTokenRequest request) throws Exception {
    final JWTToken tokens = jwtCache.refreshToken(request.getRefreshToken());
    RefreshTokenResponse response = new RefreshTokenResponse();
    response.setAccessToken(tokens.getToken());
    response.setExpiresIn(tokens.getExpiresIn().intValue());
    response.setRefreshToken(tokens.getRefreshToken());
    response.setRefreshExpiresIn(tokens.getRefreshExpiresIn().intValue());
    return JsonResponse.ok(response);
  }
}
