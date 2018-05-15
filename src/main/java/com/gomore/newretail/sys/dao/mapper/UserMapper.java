package com.gomore.newretail.sys.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.gomore.newretail.sys.bean.dto.user.UserDTO;
import com.gomore.newretail.sys.bean.dto.user.UserPageFilter;
import com.gomore.newretail.sys.dao.po.UserPO;

/**
 * User 表数据库控制层接口
 *
 * @author Debenson
 * @since 0.1
 */
public interface UserMapper extends BaseMapper<UserPO> {

  /**
   * 用户筛选
   * 
   * @param page
   *          分页条件
   * @param filter
   *          筛选条件
   * @return
   */
  List<UserDTO> query(Pagination page, @Param("filter") UserPageFilter filter);

}