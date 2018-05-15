package com.gomore.newretail.sys.dao.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.gomore.experiment.commons.dao.po.StandardEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "nr_sys_app_version")
public class AppVersionPO extends StandardEntity {
  private static final long serialVersionUID = 2870627208312580202L;

  /**
   * 版本号
   */
  @TableField("version_code")
  private Long versionCode;

  /**
   * 版本名称
   */
  @TableField("version_name")
  private String versionName;

  /**
   * 安装包类型
   */
  private EnumAppType type;

  /**
   * 升级包下载地址
   */
  @TableField("download_url")
  private String downloadUrl;

  /**
   * 是否强制更新
   */
  @TableField("force_udpate")
  private boolean forceUpdate;

  /**
   * 升级内容
   */
  private String desciption;

}
