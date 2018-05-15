/**
 * 版权所有(C)，上海勾芒信息科技，2018，所有权利保留。
 * <p>
 * 项目名：	newretail-sys-service
 * 文件名：	MenuTreeDTO.java
 * 模块说明：
 * 修改历史：
 * 2018年2月10日 - Debenson - 创建。
 */
package com.gomore.newretail.sys.bean.dto.menu;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.springframework.util.Assert;

import com.gomore.newretail.sys.converter.MenuConverter;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单树型结构
 *
 * @author Debenson
 * @since 0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "菜单树型结构")
public class MenuTreeDTO extends MenuDTO {
  private static final long serialVersionUID = 7273398072787885985L;
  public static final String ROOT_MENU = "root";

  /**
   * 下级菜单
   */
  @ApiModelProperty("下级菜单")
  private List<MenuTreeDTO> children;

  /**
   * 构造菜单树，顶级菜单为虚拟菜单。
   * 
   * @param sources
   * @return
   */
  public static MenuTreeDTO buildTree(List<MenuDTO> sources) {
    final MenuTreeDTO root = MenuTreeDTO.root();
    // removeDuplicate(sources);
    root.resetChildren(sources);
    return root;
  }

  // /**
  // * 去重
  // *
  // * @param sources
  // */
  // private static void removeDuplicate(List<MenuDTO> sources) {
  // Map<Long, MenuDTO> map = Maps.newHashMap();
  // for (MenuDTO menu : sources) {
  // map.put(menu.getId(), menu);
  // }
  // sources.clear();
  // sources.addAll(map.values());
  // }

  /**
   * 返回顶级菜单
   *
   * @return
   */
  private static MenuTreeDTO root() {
    MenuTreeDTO root = new MenuTreeDTO();
    root.setId(null);
    root.setParentId(null);
    root.setName(ROOT_MENU);
    root.setChildren(Lists.newArrayList());
    return root;
  }

  /**
   * 初始化子菜单
   *
   * @param sources
   *          来源菜单列表
   */
  private void resetChildren(List<MenuDTO> sources) {
    Iterator<MenuDTO> it = sources.iterator();
    while (it.hasNext()) {
      final MenuDTO child = it.next();
      if (Objects.equals(getId(), child.getParentId())) {
        this.addChild(child);
        it.remove();
      }
    }

    // 设置子菜单的子菜单
    if (getChildren() != null) {
      for (MenuTreeDTO child : getChildren()) {
        child.resetChildren(sources);
      }
    }
  }

  /**
   * 添加子菜单
   *
   * @param child
   *          子菜单
   * @return
   */
  public MenuTreeDTO addChild(MenuDTO child) {
    Assert.notNull(child, "子菜单不能为空");

    if (getChildren() == null) {
      setChildren(Lists.newArrayList());
    }
    getChildren().add(MenuConverter.INSTANCE.toMenuTreeDTO(child));
    return this;
  }

}
