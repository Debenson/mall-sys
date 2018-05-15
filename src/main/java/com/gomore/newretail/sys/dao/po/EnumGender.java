package com.gomore.newretail.sys.dao.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IEnum;

/**
 * 测试会员性别
 *
 * @author tom
 */
public enum EnumGender implements IEnum {
  /**
   * 男性
   */
  MALE("M"),

  /**
   * 女性
   */
  FEMALE("F"),

  /**
   * 未知
   */
  UNKNOWN("-");

  private String value;

  EnumGender(String value) {
    this.value = value;
  }

  @Override
  public Serializable getValue() {
    return value;
  }

}
