package com.gomore.newretail.sys.aspect;


import com.gomore.experiment.commons.util.JsonUtil;
import com.gomore.experiment.commons.util.SpringContextUtils;
import com.gomore.newretail.commons.util.IPUtils;
import com.gomore.newretail.sys.annotation.SysLog;
import com.gomore.newretail.sys.dao.mapper.SystemLogMapper;
import com.gomore.newretail.sys.dao.po.SystemLogPO;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


/**
 * @author liyang
 * @since 0.1
 */
@Aspect
@Component
public class SysLogAspect {
  @Autowired
  private SystemLogMapper systemLogMapper;
  
  @Pointcut("@annotation(com.gomore.newretail.sys.annotation.SysLog)")
  public void logPointCut() {
  
  }
  
  @Around("logPointCut()")
  public Object around(ProceedingJoinPoint point) throws Throwable {
    long beginTime = System.currentTimeMillis();
    //返回结果
    Object result = point.proceed();
    //执行时长(毫秒)
    long time = System.currentTimeMillis() - beginTime;
    
    //保存日志
    saveSysLog(point, time, result);
    return result;
  }
  
  private void saveSysLog(ProceedingJoinPoint joinPoint, long time, Object result) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    
    SystemLogPO sysLog = new SystemLogPO();
    SysLog sysLogAn = method.getAnnotation(SysLog.class);
    if (sysLogAn != null) {
      sysLog.setOperation(sysLogAn.value());
      sysLog.setModule(sysLogAn.module());
    }
    
    //请求的方法名
    String className = joinPoint.getTarget().getClass().getName();
    String methodName = signature.getName();
    sysLog.setMethod(className + "." + methodName + "()");
    
    //请求的参数
    Object[] args = joinPoint.getArgs();
    try {
      sysLog.setParams(JsonUtil.toJson(args));
      sysLog.setResult(JsonUtil.toJson(result));
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    //获取request
    HttpServletRequest request = SpringContextUtils.getRequest();
    //设置IP地址
    sysLog.setIp(IPUtils.getIpAddr(request));
    
    //用户名
    sysLog.setUser(JsonUtil.toJson(SecurityUtils.getSubject().getPrincipal()));
    sysLog.setTime(time);
    //保存系统日志
    systemLogMapper.insert(sysLog);
  }
  
}
