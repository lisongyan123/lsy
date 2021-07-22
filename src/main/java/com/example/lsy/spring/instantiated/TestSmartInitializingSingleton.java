package com.example.lsy.spring.instantiated;

import com.example.lsy.LsyApplication;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

/**
 * spring容器管理的所有单例对象（非懒加载对象）初始化完成之后调用的回调接口。其触发时机为postProcessAfterInitialization之后
 * 使用场景：用户可以扩展此接口在对所有单例对象初始化完毕后，做一些后置的业务处理
 */

@Component
public class TestSmartInitializingSingleton implements SmartInitializingSingleton {

    private ListableBeanFactory beanFactory;

    public TestSmartInitializingSingleton(ListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterSingletonsInstantiated() {
        System.out.println("[TestSmartInitializingSingleton]");

        String[] beanNames = beanFactory.getBeanNamesForType(LsyApplication.class);
        for (String s : beanNames) {
            System.out.println(s);
        }
    }

}