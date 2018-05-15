package com.gomore.newretail.sys.dao.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IEnum;

public enum EnumAppType implements IEnum {
  /**
   * iOS
   */
  ipa,

  /**
   * Android
   */
  apk;

  @Override
  public Serializable getValue() {
    return name();
  }
}
