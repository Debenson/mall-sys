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

import com.gomore.newretail.commons.constants.Constants;
import com.gomore.newretail.sys.bean.dto.organization.OrganizationCountDTO;
import com.gomore.newretail.sys.bean.dto.organization.OrganizationDTO;
import com.gomore.newretail.sys.dao.po.OrganizationPO;

/**
 * 菜单转换器
 *
 * @author Debenson
 * @since 0.1
 */
@Mapper(componentModel = Constants.COMPONENT_MODEL_SPRING)
public interface OrganizationConverter {

  /**
   * DTO转PO
   *
   * @param dto
   * @return
   */
  OrganizationPO toOrg(OrganizationDTO dto);

  /**
   * PO转DTO
   *
   * @param po
   * @return
   */
  OrganizationDTO toOrgDTO(OrganizationPO po);

  /**
   * PO列表转DTO列表
   *
   * @param pos
   * @return
   */
  List<OrganizationDTO> toOrgDTOs(List<OrganizationPO> pos);

  /**
   * po -> countDto
   * 
   * @param po
   * @return
   */
  OrganizationCountDTO toOrganizationCountDTO(OrganizationPO po);

  /**
   * poList -> countDtoList
   * 
   * @param pos
   * @return
   */
  List<OrganizationCountDTO> toOrganizationCountDTOs(List<OrganizationPO> pos);
}
