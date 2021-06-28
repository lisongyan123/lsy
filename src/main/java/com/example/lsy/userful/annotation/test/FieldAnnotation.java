package com.example.lsy.userful.annotation.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jiangqiang
 * @version 2.0.1
 * @date 2018/4/25
 **/
@Target(ElementType.FIELD)//Field declaration (includes enum constants)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldAnnotation {
   int length() default 0;
   String description() default "";
}