package com.gomore.newretail.sys.bean.dto.app;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javax.validation.constraints.NotBlank;

import com.gomore.experiment.commons.dao.dto.BaseDTO;
import com.gomore.newretail.sys.dao.po.EnumAppType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名称: newretail-parent 模块描述:
 * <p>
 * 2018-03-07 15:15 - created by liyang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "更新版本")
public class UpdateAppVersionDTO extends BaseDTO {
  private static final long serialVersionUID = -4084277450232078902L;

  @NotBlank
  @ApiModelProperty(value = "版本号", required = true)
  private Long versionCode;

  @NotBlank
  @ApiModelProperty(value = "版本名称", required = true, allowableValues = "range[0,20]")
  private String versionName;

  @ApiModelProperty("升级包下载地址")
  @NotBlank
  @Size(min = 1, max = 128, message = "下载地址长度必须大于1小于128个字符")
  private String downloadUrl;

  @NotNull
  @ApiModelProperty(value = "安装包类型", required = true)
  private EnumAppType type;

  @ApiModelProperty("是否强制更新")
  private Boolean forceUpdate;

  @Size(max = 512, message = "内容最多为512个字符")
  @ApiModelProperty(value = "升级内容", required = false, allowableValues = "range[1,512]")
  private String desciption;

}
