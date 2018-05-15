package com.gomore.newretail.sys.dao.po;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * @author liyang
 * @since 0.1
 */
public enum EnumLogModule implements IEnum {
  /**
   * 其他
   */
  other;
  
  @Override
  public Serializable getValue() {
    return name();
  }
}
