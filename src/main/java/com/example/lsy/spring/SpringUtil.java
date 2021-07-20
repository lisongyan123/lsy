package com.example.lsy.spring;
import org.springframework.beans.BeansException; 
import org.springframework.context.ApplicationContext; 
import org.springframework.context.ApplicationContextAware; 
import org.springframework.stereotype.Component;

/**
 * springboot 更多扩展点 https://www.cnblogs.com/bryan31/p/13346588.html?utm_source=tuicool
 * springmvc 扩展点 https://www.cnblogs.com/zhangjianbin/p/7903295.html
 */
@Component 
public class SpringUtil implements ApplicationContextAware{ 
  private static ApplicationContext applicationContext; 
  @Override 
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException { 
    if(SpringUtil.applicationContext == null) { 
      SpringUtil.applicationContext = applicationContext; 
    } 
  } 
  public static ApplicationContext getApplicationContext() { 
    return applicationContext; 
  } 
  public static Object getBean(String name){ 
    return getApplicationContext().getBean(name); 
  } 
  public static <T> T getBean(Class<T> clazz){ 
    return getApplicationContext().getBean(clazz); 
  } 
  public static <T> T getBean(String name,Class<T> clazz){ 
    return getApplicationContext().getBean(name, clazz); 
  } 
} 