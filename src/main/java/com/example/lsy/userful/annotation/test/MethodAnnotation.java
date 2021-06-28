package com.example.lsy.userful.annotation.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jiangqiang
 * @version 2.0.1
 * @date 2018/4/26
 **/
@Target(ElementType.METHOD)//Method declaration
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodAnnotation {
    String author() default "jianqiang";
    String version() default "1.0";
    String description() default "";
}