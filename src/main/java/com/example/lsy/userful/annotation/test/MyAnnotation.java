package com.example.lsy.userful.annotation.test;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})  //允许作用在方法和属性字段上
public @interface MyAnnotation {
    int id() default 0;

    String[] value();
}
