package com.gomore.newretail.sys.bean.dto.app;

import com.gomore.experiment.commons.dao.dto.StandardDTO;
import com.gomore.newretail.sys.dao.po.EnumAppType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liyang
 * @since 0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "App版本信息")
public class AppVersionDTO extends StandardDTO {
  private static final long serialVersionUID = -7967831948845788733L;

  @ApiModelProperty(value = "版本号,约定规则为major(1位)+minor(2位)+point(3位) 例如:100000")
  private Long versionCode;

  @ApiModelProperty("版本名称,约定规则为major(1位).minor(2位).point(3位) 例如:1.00.000")
  private String versionName;

  @ApiModelProperty("安装包类型")
  private EnumAppType type;

  @ApiModelProperty("升级包下载地址")
  private String downloadUrl;

  @ApiModelProperty("是否强制更新")
  private Boolean forceUpdate;

  @ApiModelProperty("升级内容")
  private String desciption;

}
