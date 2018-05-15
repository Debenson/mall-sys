/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-service
 * 文件名：	MenuConverter.java
 * 模块说明：
 * 修改历史：
 * 2018年2月10日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.converter;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.gomore.newretail.commons.constants.Constants;
import com.gomore.newretail.sys.bean.dto.menu.MenuDTO;
import com.gomore.newretail.sys.bean.dto.menu.MenuTreeDTO;
import com.gomore.newretail.sys.dao.po.MenuPO;

/**
 * 菜单转换器
 *
 * @author Debenson
 * @since 0.1
 */
@Mapper(componentModel = Constants.COMPONENT_MODEL_SPRING)
public interface MenuConverter {
  MenuConverter INSTANCE = Mappers.getMapper(MenuConverter.class);

  /**
   * DTO转PO
   *
   * @param dto
   * @return
   */
  MenuPO toMenu(MenuDTO dto);

  /**
   * PO转DTO
   *
   * @param po
   * @return
   */
  MenuDTO toMenuDTO(MenuPO po);

  /**
   * 转菜单树
   * 
   * @param dto
   * @return
   */
  @Mapping(target = "children", ignore = true)
  MenuTreeDTO toMenuTreeDTO(MenuDTO dto);

  /**
   * PO列表转DTO列表
   *
   * @param pos
   * @return
   */
  List<MenuDTO> toMenuDTOs(List<MenuPO> pos);

}
