package com.gomore.newretail.sys.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.gomore.newretail.commons.constants.Constants;
import com.gomore.newretail.sys.bean.dto.app.AppVersionDTO;
import com.gomore.newretail.sys.dao.po.AppVersionPO;

/**
 * @author liyang
 * @since 0.1
 */
@Mapper(componentModel = Constants.COMPONENT_MODEL_SPRING)
public interface AppVersionConverter {
  AppVersionConverter INSTANCE = Mappers.getMapper(AppVersionConverter.class);

  /**
   * DTO转PO
   *
   * @param dto
   * @return
   */
  AppVersionPO toAppVersion(AppVersionDTO dto);

  /**
   * PO转DTO
   *
   * @param po
   * @return
   */
  AppVersionDTO toAppVersionDTO(AppVersionPO po);

}
