package com.gomore.newretail.sys.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gomore.newretail.sys.bean.dto.app.AppVersionDTO;
import com.gomore.newretail.sys.converter.AppVersionConverter;
import com.gomore.newretail.sys.dao.mapper.AppVersionMapper;
import com.gomore.newretail.sys.dao.po.AppVersionPO;
import com.gomore.newretail.sys.dao.po.EnumAppType;
import com.gomore.newretail.sys.service.AppService;

/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名称: newretail-parent 模块名称: APP服务接口 模块描述:
 * <p>
 * 2018-03-05 13:25 - created by liyang
 */
@Service
public class AppServiceImpl implements AppService {

  @Autowired
  private AppVersionMapper appMapper;

  @Autowired
  private AppVersionConverter appVersionConverter;

  @Override
  public AppVersionDTO getLastestVersion(EnumAppType type, Long version, String osVersion) {
    Assert.notNull(type, "安装包类型不能为空");
    Assert.notNull(version, "版本号不能为空");

    List<AppVersionPO> appVersions = appMapper
        .selectList(new EntityWrapper<AppVersionPO>().eq("type", type).gt("version_code", version));

    if (appVersions.isEmpty()) {
      return null;
    } else {
      final AppVersionPO po = Collections.max(appVersions, new Comparator<AppVersionPO>() {
        @Override
        public int compare(AppVersionPO o1, AppVersionPO o2) {
          return o1.getVersionCode().compareTo(o2.getVersionCode());
        }
      });
      return appVersionConverter.toAppVersionDTO(po);
    }
  }

  @Override
  public Long createAppVersion(AppVersionDTO appVersion) {
    Assert.notNull(appVersion, "版本信息不能为空");
    Assert.isNull(appVersion.getId(), "版本ID必须为空");

    final AppVersionPO po = appVersionConverter.toAppVersion(appVersion);
    appMapper.insert(po);
    return po.getId();
  }

  @Override
  public void updateAppVersion(AppVersionDTO appVersion) {
    Assert.notNull(appVersion, "版本不能为空");
    Assert.notNull(appVersion.getId(), "版本ID不能为空");

    AppVersionPO po = appVersionConverter.toAppVersion(appVersion);
    appMapper.updateById(po);
  }

}
