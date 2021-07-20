package com.example.lsy.spring.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 启动即实现 最后执行 调用 callRunners 和 ApplicationRunner 一起实现
 */
@Component
@Order(1)
public class RunnerLoadOne implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        System.out.println("------------容器初始化bean之后,加载资源结束-----------");
    }
}

@Component
@Order(2)
class RunnerLoadTwo implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("依赖预先加载的资源数据：" );
    }
}
