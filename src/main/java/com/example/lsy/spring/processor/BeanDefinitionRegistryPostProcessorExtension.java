package com.example.lsy.spring.processor;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class BeanDefinitionRegistryPostProcessorExtension implements BeanDefinitionRegistryPostProcessor {


	// 此接口是BeanFactoryPostProcessor的方法，
	// 这里也需要重写是因为BeanDefinitionRegistryPostProcessor继承自BeanFactoryPostProcessor
	// 如果我们只需要修改bean定义，那么只需实现BeanFactoryPostProcessor即可
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// 由于这里展示的是BeanDefinitionRegistryPostProcessor的使用，这里就不做操作了
	}

	/**
	 * 注册bean定义接口
	 *
	 * @param registry the bean definition registry used by the application context
	 * @throws BeansException
	 */
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		// new一个RootBeanDefinition，传入我们需要加入spring容器的Class对象
		RootBeanDefinition beanDefinition = new RootBeanDefinition(Person.class);
		// 这里是给我们的对象赋值，如果不需要进行赋值，那么下面这里可忽略
		MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
		 // key为属性名称，value为属性值
		propertyValues.add("age", 10);
		propertyValues.add("name", "list");
		// 将我们自定义的bean定义加入到spring。
		registry.registerBeanDefinition("person1", beanDefinition);
	}
	@Data
	class Person {
		Integer age;
		String name;
	}

}
