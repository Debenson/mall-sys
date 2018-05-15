/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-api
 * 文件名：	InsertOrganizationConverter.java
 * 模块说明：
 * 修改历史：
 * 2018年2月10日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.converter;

import org.mapstruct.Mapper;

import com.gomore.newretail.commons.constants.Constants;
import com.gomore.newretail.sys.bean.dto.organization.CreateOrganizationDTO;
import com.gomore.newretail.sys.bean.dto.organization.OrganizationDTO;
import com.gomore.newretail.sys.bean.dto.organization.UpdateOrganizationDTO;

/**
 * @author Debenson
 * @since 0.1
 */
@Mapper(componentModel = Constants.COMPONENT_MODEL_SPRING)
public interface CreateOrgConverter {

  /**
   * 组织插入数据转DTO
   *
   * @param dto
   * @return
   */
  OrganizationDTO toOrgDTO(CreateOrganizationDTO dto);

  /**
   * 组织更新数据转DTO
   *
   * @param dto
   * @return
   */
  OrganizationDTO toOrgDTO(UpdateOrganizationDTO dto);

}
