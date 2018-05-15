/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * 
 * 项目名：	newretail-sys-service
 * 文件名：	UserPageFilter.java
 * 模块说明：	
 * 修改历史：
 * 2018年2月6日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.bean.dto.user;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.gomore.experiment.commons.rest.FilterWithOrder;
import com.gomore.experiment.commons.rest.PageFilter;
import com.gomore.experiment.commons.rest.QueryOrderDirection;
import com.gomore.newretail.sys.dao.po.EnumGender;
import com.gomore.newretail.sys.dao.po.UserPO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户分页查询
 * 
 * @author Debenson
 * @since 0.1
 */
@Data
@ApiModel(description = "用户分页查询条件")
@EqualsAndHashCode(callSuper = false)
public class UserPageFilter extends PageFilter<UserPO> implements FilterWithOrder {
  private static final long serialVersionUID = -7898596668414853569L;

  /**
   * 用户标识等于
   */
  @ApiModelProperty(value = "用户标识等于")
  @XmlElement(required = true, name = "")
  private Long idEquals;
  /** 昵称或用户名类似于 */
  @ApiModelProperty(value = "昵称或用户名类似于")
  private String nameLike;
  /** 工号等于 */
  @ApiModelProperty(value = "工号等于")
  private String workNumberEquals;
  /** 性别等于 */
  @ApiModelProperty(value = "性别等于")
  private EnumGender genderEquals;
  /** 是否禁用，如果为null表示忽略该条件 */
  @ApiModelProperty(value = "查询禁用状态的用户，如果为null表示忽略该条件")
  private Boolean disabled;
  /**
   * 所属组织标识等于
   */
  @ApiModelProperty(value = "所属组织标识等于")
  private Long upperOrgIdEqulas;

  /** 排序字段，支持name（姓名）， gender（性别）,disabled（状态）,updateTime（最后更新时间） */
  @ApiModelProperty(value = "排序字段", allowableValues = "name,gender,disabled,update_time")
  private String orderField;

  /** 排序方向 */
  @ApiModelProperty(value = "排序方向")
  private QueryOrderDirection orderDirection = QueryOrderDirection.asc;

  private static final String FIELD_REAL_NAME = "real_name";
  private static final String GENDER = "gender";
  private static final String DISABLED = "disabled";
  private static final String UPDATETIME = "update_time";

  @Override
  public Pagination toPagination() {
    Pagination page = super.toPagination();
    if (StringUtils.isBlank(orderField)) {
      return page;
    }

    if (FIELD_REAL_NAME.equals(orderField)) {
      page.setOrderByField(orderField);
    } else if (GENDER.equals(orderField)) {
      page.setOrderByField(orderField);
    } else if (DISABLED.equals(orderField)) {
      page.setOrderByField(orderField);
    } else if (UPDATETIME.equals(orderField)) {
      page.setOrderByField(orderField);
    }
    page.setAsc(!QueryOrderDirection.desc.equals(orderDirection));
    return page;
  }

}
