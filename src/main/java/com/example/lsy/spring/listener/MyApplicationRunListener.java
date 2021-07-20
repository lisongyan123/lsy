package com.example.lsy.spring.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * SpringApplicationRunListener属于应用程序启动层面的监听器,在springboot启动时候,调用run方法进行反射加载初始化。
 * 此时上下文还没有加载，如果通过@Compnant是起不了作用的,spring的监听器
 */
public class MyApplicationRunListener implements SpringApplicationRunListener {

  private final SpringApplication application;
  private final String[] args;

  public MyApplicationRunListener(SpringApplication sa, String[] args) {
    this.application = sa;
    this.args = args;
  }

  @Override
  public void starting() {
    System.out.println("服务启动RunnerTest  SpringApplicationRunListener的starting方法...");
  }

  @Override
  public void environmentPrepared(ConfigurableEnvironment environment) {
    System.out.println("服务启动RunnerTest  SpringApplicationRunListener的environmentPrepared方法...");
  }

  // 上下文建立好的时候
  @Override
  public void contextPrepared(ConfigurableApplicationContext context) {
    System.out.println("服务启动RunnerTest  SpringApplicationRunListener的contextPrepared方法...");
  }

  // 上下文载入配置时候
  @Override
  public void contextLoaded(ConfigurableApplicationContext context) {
    System.out.println("服务启动RunnerTest  SpringApplicationRunListener的contextLoaded方法...");
  }
  // 上下文刷新完成后，run方法执行完之前
  @Override
  public void running(ConfigurableApplicationContext context) {
    System.out.println("服务启动RunnerTest  SpringApplicationRunListener的running方法...");
  }

  @Override
  public void failed(ConfigurableApplicationContext context, Throwable exception) {
    System.out.println("服务启动RunnerTest  SpringApplicationRunListener的failed方法...");
  }

  @Override
  public void started(ConfigurableApplicationContext context) {
    System.out.println("服务启动RunnerTest  SpringApplicationRunListener的started方法...");
  }
}
