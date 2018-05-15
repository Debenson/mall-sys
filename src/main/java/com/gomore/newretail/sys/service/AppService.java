package com.gomore.newretail.sys.service;

import com.gomore.newretail.sys.bean.dto.app.AppVersionDTO;
import com.gomore.newretail.sys.dao.po.EnumAppType;

/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名称: newretail-parent 模块名称: App服务 模块描述: App服务接口;
 * <p>
 * 2018-03-05 13:22 - created by liyang
 */
public interface AppService {

  /**
   * 获取最新版本信息
   *
   * @param type
   *          安装包类型
   * @param version
   *          客户端版本
   * @param osVersion
   *          客户端操作系统
   * @return 找不到返回 null
   */
  AppVersionDTO getLastestVersion(EnumAppType type, Long version, String osVersion);

  /**
   * 新增版本信息
   *
   * @param appVersion
   *          版本信息
   * @return 新建版本id
   */
  Long createAppVersion(AppVersionDTO appVersion);

  /**
   * 更新版本信息
   *
   * @param appVersion
   *          版本信息
   * @return
   */
  void updateAppVersion(AppVersionDTO appVersion);

}
