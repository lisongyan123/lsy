package com.example.lsy.spring.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
/**
 * SpringBoot内置的一些ApplicationContextInitializer
 *
 * DelegatingApplicationContextInitializer
 * 使用环境属性context.initializer.classes指定的初始化器(initializers)进行初始化工作，如果没有指定则什么都不做。
 * 通过它使得我们可以把自定义实现类配置在application.properties里成为了可能。
 *
 * ContextIdApplicationContextInitializer
 * 设置Spring应用上下文的ID,会参照环境属性。至于Id设置为啥值会参考环境属性：
 * spring.application.name
 * vcap.application.name
 * spring.config.name
 * spring.application.index
 * vcap.application.instance_index
 *
 * 如果这些属性都没有，ID使用application。
 *
 * ConfigurationWarningsApplicationContextInitializer
 * 对于一般配置错误在日志中作出警告
 *
 * ServerPortInfoApplicationContextInitializer
 * 将内置servlet容器实际使用的监听端口写入到Environment环境属性中。这样属性local.server.port就可以直接通过@Value注入到测试中，或者通过环境属性Environment获取。
 *
 * SharedMetadataReaderFactoryContextInitializer
 * 创建一个SpringBoot和ConfigurationClassPostProcessor共用的CachingMetadataReaderFactory对象。实现类为：ConcurrentReferenceCachingMetadataReaderFactory
 *
 * ConditionEvaluationReportLoggingListener
 * 将ConditionEvaluationReport写入日志。
 */

/**
 * spring容器刷新之前初始化Spring ConfigurableApplicationContext的回调接口
 * 需要对应用程序上下文进行编程初始化的web应用程序中。例如，根据上下文环境注册属性源或激活配置文件等。
 * SpringApplication 的 run 方法中 context 在refresh之前执行
 * 注册属性源(property sources)或者针对上下文的环境信息environment激活相应的profile
 */
public class MyApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("-----MyApplicationContextInitializer initialize-----" + this.getClass().getSimpleName());

        // 打印容器里面有多少个bean
        System.out.println("bean count=====" + applicationContext.getBeanDefinitionCount());

        // 打印人所有 beanName
        System.out.println(applicationContext.getBeanDefinitionCount() + "个Bean的名字如下：");
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            System.out.println(beanName);
        }
    }
}

