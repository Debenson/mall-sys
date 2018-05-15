/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-service
 * 文件名：	UserServiceImpl.java
 * 模块说明：
 * 修改历史：
 * 2018年2月2日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.gomore.experiment.commons.annotation.Tx;
import com.gomore.experiment.commons.exception.ServiceException;
import com.gomore.experiment.commons.rest.PageResult;
import com.gomore.newretail.commons.constants.Constants;
import com.gomore.newretail.commons.exception.MyExceptionCode;
import com.gomore.newretail.commons.util.PasswordUtils;
import com.gomore.newretail.sys.bean.dto.role.RoleDTO;
import com.gomore.newretail.sys.bean.dto.user.UserDTO;
import com.gomore.newretail.sys.bean.dto.user.UserPageFilter;
import com.gomore.newretail.sys.converter.UserConverter;
import com.gomore.newretail.sys.dao.mapper.RoleMapper;
import com.gomore.newretail.sys.dao.mapper.UserMapper;
import com.gomore.newretail.sys.dao.po.RolePO;
import com.gomore.newretail.sys.dao.po.UserPO;
import com.gomore.newretail.sys.dao.po.UserRoleRPO;
import com.gomore.newretail.sys.service.UserService;
import com.google.common.collect.Sets;

/**
 * 用户服务实现
 *
 * @author Debenson
 * @since 0.1
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserMapper userMapper;
  @Autowired
  private RoleMapper roleMapper;
  @Autowired
  private UserConverter userConverter;

  @Override
  public Long create(UserDTO user) {
    Assert.notNull(user, "用户不能为空");
    Assert.isNull(user.getId(), "用户的ID必须为空");

    Wrapper<UserPO> wrapper = new EntityWrapper<UserPO>();
    wrapper.eq("username", user.getUsername());
    int count = userMapper.selectCount(wrapper);
    if (count > 0) {
      throw new ServiceException(MyExceptionCode.memberNotRegister);
    }

    // 生成密码
    final String salt = UUID.randomUUID().toString().replaceAll("-", "");
    user.setSalt(salt);
    user.setPassword(PasswordUtils.encrypt(user.getPassword(), user.getSalt()));

    UserPO po = userConverter.toUser(user);

    userMapper.insert(po);
    return po.getId();
  }

  @Override
  public UserDTO getById(Long id) {
    Assert.notNull(id, "用户标识不能为空");

    UserPageFilter filter = new UserPageFilter();
    filter.setIdEquals(id);
    final Pagination page = filter.toPagination();
    final List<UserDTO> queryResults = userMapper.query(page, filter);
    return queryResults.isEmpty() ? null : queryResults.get(0);
  }

  @Override
  public UserDTO getRootAdmin() {
    return getById(UserPO.ROOT_USER_ID);
  }

  @Override
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public void update(UserDTO user) {
    Assert.notNull(user, "用户不能为空");
    Assert.notNull(user.getId(), "用户的ID不能为空");

    UserPO po = userConverter.toUser(user);
    userMapper.updateById(po);
  }

  @Override
  public PageResult<UserDTO> query(UserPageFilter filter) {
    Assert.notNull(filter, "查询条件不能为空");

    final Pagination page = filter.toPagination();
    final List<UserDTO> queryResults = userMapper.query(page, filter);
    return new PageResult<>(page, queryResults);
  }

  @Override
  public int deleteById(Long id) {
    Assert.notNull(id, "ID不能为空");
    return userMapper.deleteById(id);
  }

  @Override
  public void disable(Long id) {
    Assert.notNull(id, "用户标识不能为空");

    UserPO user = userMapper.selectById(id);
    if (user == null) {
      throw new ServiceException(MyExceptionCode.userNotFound);
    }
    if (user.getDisabled() != null && user.getDisabled()) {
      return;
    }

    UserPO updateUser = new UserPO();
    updateUser.setId(user.getId());
    updateUser.setDisabled(true);
    userMapper.updateById(updateUser);
  }

  @Override
  public void enable(Long id) {
    Assert.notNull(id, "用户标识不能为空");

    UserPO user = userMapper.selectById(id);
    if (user == null) {
      throw new ServiceException(MyExceptionCode.userNotFound);
    }
    if (user.getDisabled() == null || !user.getDisabled()) {
      return;
    }

    UserPO updateUser = new UserPO();
    updateUser.setId(user.getId());
    updateUser.setDisabled(false);
    userMapper.updateById(updateUser);
  }

  @Override
  public UserDTO getByUserName(String username) {
    Assert.hasText(username, "用户名不能为空");

    List<UserPO> users = userMapper
        .selectList(new EntityWrapper<UserPO>().eq("username", username));
    if (users.isEmpty()) {
      return null;
    } else {
      final UserPO po = users.get(0);
      return userConverter.toUserDTO(po);
    }
  }

  @Override
  public void updateUserRoles(Long userId, Long workingOrgId, Long... roleIds) {
    Assert.notNull(userId, "用户标识不能为空");

    final UserPO user = userMapper.selectById(userId);
    if (user == null) {
      throw new ServiceException(MyExceptionCode.userNotFound);
    }
    if (user.getDisabled() != null && user.getDisabled()) {
      throw new ServiceException(MyExceptionCode.userIsLocked);
    }

    // 默认取用户所属组织
    if (workingOrgId == null) {
      workingOrgId = user.getOrgId();
    }

    // 清除所有角色
    new UserRoleRPO().delete(
        new EntityWrapper<UserRoleRPO>().eq("user_id", userId).eq("working_org_id", workingOrgId));

    // 添加角色
    if (roleIds != null) {
      for (Long roleId : roleIds) {
        int count = roleMapper.selectCount(new EntityWrapper<RolePO>().eq("id", roleId));
        if (count <= 0) {
          throw new ServiceException(MyExceptionCode.roleNotFound);
        }

        UserRoleRPO userRole = new UserRoleRPO();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRole.setWorkingOrgId(workingOrgId);
        userRole.insert();
      }
    }
  }

  @Override
  public Set<RoleDTO> getUserRoles(Long userId, Long workingOrgId) {
    Assert.notNull(userId, "用户标识不能为空");

    final UserPO user = userMapper.selectById(userId);
    if (user == null) {
      return Sets.newHashSet();
    }

    // 默认取用户所属组织
    if (workingOrgId == null) {
      workingOrgId = user.getOrgId();
    }

    return roleMapper.getUserRoles(userId, workingOrgId);
  }

  @Override
  @Tx
  public void modifyPassword(Long userId, String oldPassword, String newPassword) {
    Assert.notNull(userId, "用户 ID不能为空");

    UserPO userPO = userMapper.selectById(userId);
    if (userPO == null) {
      throw new ServiceException(MyExceptionCode.userNotFound);
    }
    if (!Objects.equals(oldPassword, userPO.getPassword())) {
      throw new ServiceException(MyExceptionCode.oldPasswordNotMatched);
    }

    if (StringUtils.isBlank(userPO.getSalt())) {
      final String salt = UUID.randomUUID().toString().replaceAll("-", "");
      userPO.setSalt(salt);
    }
    userPO.setPassword(PasswordUtils.encrypt(newPassword, userPO.getSalt()));
    UserPO updateUser = new UserPO();
    updateUser.setId(userId);
    updateUser.setSalt(userPO.getSalt());
    updateUser.setPassword(userPO.getPassword());
    userMapper.updateById(updateUser);
  }

  @Override
  @Tx
  public void resetPassword(Long userId) {
    Assert.notNull(userId, "用户 ID不能为空");

    UserPO userPO = userMapper.selectById(userId);
    if (userPO == null) {
      throw new ServiceException(MyExceptionCode.userNotFound);
    }

    if (StringUtils.isBlank(userPO.getSalt())) {
      final String salt = UUID.randomUUID().toString().replaceAll("-", "");
      userPO.setSalt(salt);
    }
    userPO.setPassword(PasswordUtils.encrypt(Constants.INIT_USER_PASSWORD, userPO.getSalt()));
    UserPO updateUser = new UserPO();
    updateUser.setId(userId);
    updateUser.setSalt(userPO.getSalt());
    updateUser.setPassword(userPO.getPassword());
    userMapper.updateById(updateUser);
  }

}
