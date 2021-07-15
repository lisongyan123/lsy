package com.example.lsy.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
@RestController
public class UpdateBean {
    /**
     * 动态修改Bean     *     * @return
     */
    @GetMapping("/bean/update/{beanName}")
    public String update(@PathVariable String beanName) throws IllegalAccessException, NoSuchMethodException {
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        String[] beans = applicationContext.getBeanDefinitionNames();
        for (String bean : beans) {
            // 拿到bean的Class对象
            Class<?> beanType = applicationContext.getType(bean);
            if (beanType == null) {
                continue;
            }
            System.out.println("bean:" + bean + "beanType" + beanType);
            // 拿到当前bean类型的所有字段
//            String methodName = beanType.getMethod("said", String.class).getName();
//            String methodName = beanType.getMethod("said",Object.class).getName();
//            System.out.println("methodName :" + methodName);
            Field[] declaredFields = beanType.getDeclaredFields();
            if (beanName.equals(bean)) {
                for (Field field : declaredFields) {
                    // 从spring容器中拿到这个具体的bean对象
                    Object beanObject = applicationContext.getBean(bean);                    // 当前字段设置新的值
                    String fieldName = field.getName();
                    if ("name".equals(fieldName)) {
                        setFieldData(field, beanObject, "AL113A5");
                    } else if ("id".equals(fieldName)) {
                        setFieldData(field, beanObject, "12");
                    }
                }
            }
        }
        return "update Bean Success";
    }

    private void setFieldData(Field field, Object bean, String data) throws IllegalAccessException {
        field.setAccessible(true);
        Class<?> type = field.getType();
        if (type.equals(String.class)) {
            field.set(bean, data);
        } else if (type.equals(Integer.class)) {
            field.set(bean, Integer.valueOf(data));
        } else if (type.equals(Long.class)) {
            field.set(bean, Long.valueOf(data));
        } else if (type.equals(Double.class)) {
            field.set(bean, Double.valueOf(data));
        } else if (type.equals(Short.class)) {
            field.set(bean, Short.valueOf(data));
        } else if (type.equals(Byte.class)) {
            field.set(bean, Byte.valueOf(data));
        } else if (type.equals(Boolean.class)) {
            field.set(bean, Boolean.valueOf(data));
        } else if (type.equals(Date.class)) {
            field.set(bean, new Date(Long.parseLong(data)));
        }
    }

}
