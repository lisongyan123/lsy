package com.example.lsy.userful.annotation.test;

import sun.nio.cs.US_ASCII;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MyAnnotationTest {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User user = new User();
        Class<User> userClass = User.class;
        Method method = userClass.getMethod("getUser",String.class,int.class);
        method.invoke(user,"Rose",24);
        if(method.isAnnotationPresent(MyAnnotation.class)) {
            MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
            String[] value = annotation.value();
            Arrays.asList(value).forEach(System.out::println);
        } else System.out.println("没有用MyAnnotation注解");

        Annotation[] annotations = method.getAnnotations();
        Arrays.asList(annotations).forEach(System.out::println);
    }
}
