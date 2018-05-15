package com.gomore.newretail.sys.annotation;

import com.gomore.newretail.sys.dao.po.EnumLogModule;

import java.lang.annotation.*;

/**
 * @author liyang
 * @since 0.1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
  
  String value() default "";
  
  EnumLogModule module() default EnumLogModule.other;
  
}
