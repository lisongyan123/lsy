package com.example.lsy.userful.annotation.test;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws Exception
    {
        //类注解
        Class<AnnotationExample> annotationExampleClass = AnnotationExample.class;
        ClassAnnotation classAnnotation = annotationExampleClass.getAnnotation(ClassAnnotation.class);
        System.out.println(classAnnotation.author());
        System.out.println(classAnnotation.version());
        System.out.println(classAnnotation.description());

        //方法注解
        Method method = annotationExampleClass.getMethod("AnnotationMethod",new Class[]{Entity.class,String.class});
        MethodAnnotation methodAnnotation = method.getAnnotation(MethodAnnotation.class);
        System.out.println(methodAnnotation.author());
        System.out.println(methodAnnotation.version());
        System.out.println(methodAnnotation.description());

        //字段注解
        Class<Entity> entityClass= Entity.class;
        Field[] fields = entityClass.getDeclaredFields();
        for(Field field:fields){
           FieldAnnotation fieldAnnotation = field.getAnnotation(FieldAnnotation.class);
            System.out.println(fieldAnnotation.length());
            System.out.println(fieldAnnotation.description());
        }
        //根据字段名获取
        Field field = entityClass.getDeclaredField("name");
        FieldAnnotation entityField =  field.getAnnotation(FieldAnnotation.class);
        System.out.println(entityField.length());
        System.out.println(entityField.description());
    }
}