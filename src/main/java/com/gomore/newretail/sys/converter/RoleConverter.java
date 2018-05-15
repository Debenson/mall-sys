package com.gomore.newretail.sys.converter;

import java.util.List;

import org.mapstruct.Mapper;

import com.gomore.newretail.commons.constants.Constants;
import com.gomore.newretail.sys.bean.dto.role.RoleDTO;
import com.gomore.newretail.sys.dao.po.RolePO;

/**
 * 角色转换器
 * 
 * @author Debenson
 * @since 0.1
 */
@Mapper(componentModel = Constants.COMPONENT_MODEL_SPRING)
public interface RoleConverter {

  /**
   * DTO转PO
   *
   * @param dto
   * @return
   */
  RolePO toOrg(RoleDTO dto);

  /**
   * PO转DTO
   *
   * @param po
   * @return
   */
  RoleDTO toOrgDTO(RolePO po);

  /**
   * PO列表转DTO列表
   *
   * @param pos
   * @return
   */
  List<RoleDTO> toOrgDTOs(List<RolePO> pos);

}
