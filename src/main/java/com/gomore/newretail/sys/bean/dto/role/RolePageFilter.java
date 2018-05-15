/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * 
 * 项目名：	newretail-sys-service
 * 文件名：	RolePageFilter.java
 * 模块说明：	
 * 修改历史：
 * 2018年2月6日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.bean.dto.role;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gomore.experiment.commons.rest.FilterWithOrder;
import com.gomore.experiment.commons.rest.PageFilter;
import com.gomore.experiment.commons.rest.QueryOrderDirection;
import com.gomore.newretail.sys.dao.po.RolePO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色分页查询
 * 
 * @author Debenson
 * @since 0.1
 */
@Data
@ApiModel(description = "角色分页查询条件")
@EqualsAndHashCode(callSuper = false)
public class RolePageFilter extends PageFilter<RolePO> implements FilterWithOrder {
  private static final long serialVersionUID = -7019527647789095824L;

  /** 名称类似于 */
  @ApiModelProperty(value = "名称类似于")
  private String nameLike;
  /**
   * 角色所属组织ID属于
   */
  @ApiModelProperty(value = "角色所属组织ID属于")
  private List<Long> orgIdIn;

  /** 排序字段，支持name（组织名称）， org_id（所属组织ID）,update_time（最后更新时间） */
  @ApiModelProperty(value = "排序字段", allowableValues = "name,org_id,update_time")
  private String orderField;

  /**
   * 上级组织路径起始于(不需要客户端传递，直接取当前登录用户所属组织path)
   */
  @JsonIgnore
  @ApiModelProperty(hidden = true)
  private String upperOrgPathStartWith;

  /** 排序方向 */
  @ApiModelProperty(value = "排序方向")
  private QueryOrderDirection orderDirection = QueryOrderDirection.asc;

  private static final String FIELD_NAME = "name";
  private static final String FIELD_ORG_ID = "org_id";
  private static final String UPDATETIME = "update_time";

  @Override
  public Pagination toPagination() {
    Pagination page = super.toPagination();
    if (StringUtils.isBlank(orderField)) {
      return page;
    }
    if (FIELD_NAME.equals(orderField)) {
      page.setOrderByField("o.name");
    } else if (FIELD_ORG_ID.equals(orderField)) {
      page.setOrderByField("o." + orderField);
    } else if (UPDATETIME.equals(orderField)) {
      page.setOrderByField("o." + orderField);
    }
    page.setAsc(!QueryOrderDirection.desc.equals(orderDirection));
    return page;
  }

}
