package com.gomore.newretail.sys.bean.dto.app;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名称: newretail-parent
 * 模块描述:
 * <p>
 * 2018-03-07 15:14 - created by liyang
 */

import com.gomore.newretail.sys.dao.po.EnumAppType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增版本数据
 *
 * @author liyang
 * @since 0.1
 */
@Data
@ApiModel(description = "新增版本")
public class CreateAppVersionDTO implements Serializable {
  private static final long serialVersionUID = 495807975399420227L;
  /**
   * 版本号
   */
  @NotBlank
  @ApiModelProperty(value = "版本号", required = true)
  private Long versionCode;

  /**
   * 版本名称
   */
  @NotBlank
  @ApiModelProperty(value = "版本名称", required = true, allowableValues = "range[0,20]")
  private String versionName;

  @NotNull
  @ApiModelProperty(value = "安装包类型", required = true)
  private EnumAppType type;

  @ApiModelProperty("升级包下载地址")
  @NotBlank
  @Size(min = 1, max = 128, message = "下载地址长度必须大于1小于128个字符")
  private String downloadUrl;

  @ApiModelProperty("是否强制更新")
  private Boolean forceUpdate;

  @Size(max = 512, message = "内容最多为512个字符")
  @ApiModelProperty(value = "升级内容", required = false, allowableValues = "range[0,512]")
  private String desciption;

}
