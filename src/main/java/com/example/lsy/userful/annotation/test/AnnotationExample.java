package com.example.lsy.userful.annotation.test;

/**
 * @author jiangqiang
 * @version 2.0.1
 * @date 2018/4/26
 **/
@ClassAnnotation(author = "demo",version = "10.2",description = "测试注解类")
public class AnnotationExample {

    @MethodAnnotation(author = "jiangqiang",version = "1.3",description = "测试注解方法")
    public void AnnotationMethod(Entity entity,String content){
        System.out.println("name:"+entity.getName()+";description:"+entity.getDescription());
        System.out.println("content:"+content);
    }
}