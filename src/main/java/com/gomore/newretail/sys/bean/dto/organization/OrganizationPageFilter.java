/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-service
 * 文件名：	RolePageFilter.java
 * 模块说明：
 * 修改历史：
 * 2018年2月6日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.bean.dto.organization;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.gomore.experiment.commons.rest.FilterWithOrder;
import com.gomore.experiment.commons.rest.PageFilter;
import com.gomore.experiment.commons.rest.QueryOrderDirection;
import com.gomore.newretail.sys.dao.po.EnumOrganizationType;
import com.gomore.newretail.sys.dao.po.OrganizationPO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 组织分页查询
 *
 * @author Debenson
 * @since 0.1
 */
@Data
@ApiModel(description = "组织分页查询条件")
@EqualsAndHashCode(callSuper = false)
public class OrganizationPageFilter extends PageFilter<OrganizationPO> implements FilterWithOrder {
  private static final long serialVersionUID = 2208604472121280964L;
  
  /**
   * 组织标识等于
   */
  @ApiModelProperty(value = "组织标识等于")
  @XmlElement(required = true, name = "")
  private Long idEquals;

  /**
   * 代码等于
   */
  @ApiModelProperty(value = "代码等于")
  @XmlElement(required = true, name = "")
  private String codeEquals;

  /**
   * 名称类似于
   */
  @ApiModelProperty(value = "名称类似于")
  private String nameLike;

  /**
   * 上级组织标识等于
   */
  @ApiModelProperty(value = "上级组织标识等于")
  private Long upperOrgIdEquals;

  /**
   * 是否包含所有下级组织
   */
  @ApiModelProperty(value = "是否包含所有下级组织")
  private Boolean includeLowerOrg;

  /**
   * 组织类型等于
   */
  @ApiModelProperty(value = "组织类型等于，取值: GROUP(集团）, COMPANY（分公司），STORE（门店），DEPART（部门）")
  private List<EnumOrganizationType> orgTypesIn;

  /** 排序字段，支持name（组织名称）， org_id（上级组织ID）,updateTime（最后更新时间） */
  @ApiModelProperty(
      value = "排序字段，支持name（组织名称）， org_id（上级组织ID）, path（组织路径，可用来按上下级关系排序组织），update_time（最后更新时间）",
      allowableValues = "name,org_id,path,update_time")
  private String orderField;

  /** 排序方向 */
  @ApiModelProperty(value = "排序方向")
  private QueryOrderDirection orderDirection = QueryOrderDirection.asc;

  public static final String FIELD_NAME = "name";
  public static final String FIELD_UPPER_ORG_ID = "org_id";
  public static final String FIELD_PATH = "path";
  private static final String UPDATETIME = "update_time";

  @Override
  public Pagination toPagination() {
    Pagination page = super.toPagination();
    if (StringUtils.isBlank(orderField)) {
      return page;
    }

    final String alias = "o.";
    if (FIELD_NAME.equals(orderField)) {
      page.setOrderByField(alias + orderField);
    } else if (FIELD_UPPER_ORG_ID.equals(orderField)) {
      page.setOrderByField(alias + orderField);
    } else if (FIELD_PATH.equals(orderField)) {
      page.setOrderByField(alias + orderField);
    } else if (UPDATETIME.equals(orderField)) {
      page.setOrderByField(alias + orderField);
    }
    page.setAsc(!QueryOrderDirection.desc.equals(orderDirection));
    return page;
  }

}
