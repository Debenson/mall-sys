/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-api
 * 文件名：	CreateUserConverter.java
 * 模块说明：
 * 修改历史：
 * 2018年2月12日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.converter;

import org.mapstruct.Mapper;

import com.gomore.newretail.commons.constants.Constants;
import com.gomore.newretail.sys.bean.dto.user.CreateUserDTO;
import com.gomore.newretail.sys.bean.dto.user.UpdateUserDTO;
import com.gomore.newretail.sys.bean.dto.user.UserDTO;

/**
 * @author Debenson
 * @since 0.1
 */
@Mapper(componentModel = Constants.COMPONENT_MODEL_SPRING)
public interface CreateUserConverter {

  /**
   * 插入用户转PO
   *
   * @param dto
   * @return
   */
  UserDTO toUserDTO(CreateUserDTO dto);

  /**
   * 更新用户转PO
   *
   * @param dto
   * @return
   */
  UserDTO toUserDTO(UpdateUserDTO dto);

}
