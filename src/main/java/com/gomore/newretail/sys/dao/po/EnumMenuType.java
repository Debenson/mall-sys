package com.gomore.newretail.sys.dao.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IEnum;

/**
 * 菜单类型
 * 
 * @author Debenson
 * @since 0.1
 */
public enum EnumMenuType implements IEnum {
  /**
   * 目录
   */
  DIRECTORY,

  /**
   * 菜单
   */
  MENU,

  /**
   * 按钮
   */
  BUTTON;

  @Override
  public Serializable getValue() {
    return name();
  }

}
