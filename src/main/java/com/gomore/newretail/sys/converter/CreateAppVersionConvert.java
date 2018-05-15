package com.gomore.newretail.sys.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.gomore.newretail.commons.constants.Constants;
import com.gomore.newretail.sys.bean.dto.app.AppVersionDTO;
import com.gomore.newretail.sys.bean.dto.app.CreateAppVersionDTO;
import com.gomore.newretail.sys.bean.dto.app.UpdateAppVersionDTO;

/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名称: newretail-parent 模块描述:
 * <p>
 * 2018-03-07 15:28 - created by liyang
 */
@Mapper(componentModel = Constants.COMPONENT_MODEL_SPRING)
public interface CreateAppVersionConvert {

  CreateAppVersionConvert INSTANCE = Mappers.getMapper(CreateAppVersionConvert.class);

  /**
   * 版本插入数据转DTO
   *
   * @param dto
   * @return
   */
  AppVersionDTO toAppVersionDTO(CreateAppVersionDTO dto);

  /**
   * 版本更新数据转DTO
   *
   * @param dto
   * @return
   */
  AppVersionDTO toAppVersionDTO(UpdateAppVersionDTO dto);
}
