/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-service
 * 文件名：	PUserConverter.java
 * 模块说明：
 * 修改历史：
 * 2018年2月3日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.converter;

import org.mapstruct.Mapper;

import com.gomore.newretail.commons.constants.Constants;
import com.gomore.newretail.sys.bean.dto.user.UserDTO;
import com.gomore.newretail.sys.dao.po.UserPO;

/**
 * 用户转换器
 *
 * @author Debenson
 * @since 0.1
 */
@Mapper(componentModel = Constants.COMPONENT_MODEL_SPRING)
public interface UserConverter {

  /**
   * DTO转PO
   *
   * @param dto
   * @return
   */
  UserPO toUser(UserDTO dto);

  /**
   * PO转DTO
   *
   * @param po
   * @return
   */
  UserDTO toUserDTO(UserPO po);

}
