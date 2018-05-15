package com.gomore.newretail.sys.dao.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.gomore.experiment.commons.dao.po.StandardEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liyang
 * @since 0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "nr_sys_log")
public class SystemLogPO extends StandardEntity {
  private static final long serialVersionUID = -230944398932123206L;
  
  /**
   * 用户信息
   */
  private String user;
  /**
   * 用户操作
   */
  private String operation;
  /**
   * 请求方法
   */
  private String method;
  /**
   * 请求参数
   */
  private String params;
  /**
   * 执行时长
   */
  private Long time;
  /**
   * IP地址
   */
  private String ip;
  /**
   * 模块
   */
  private EnumLogModule module;
  
  /**
   * 返回结果
   */
  private String result;
  
  
}
