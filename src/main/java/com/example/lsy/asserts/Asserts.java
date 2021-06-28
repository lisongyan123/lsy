package com.example.lsy.asserts;
import org.springframework.util.Assert;
import java.util.HashMap;
import java.util.Map;

public class Asserts<a> {
    public static void main(String[] args) {
        Object object = new Object();
        //对象非空
        Assert.notNull(object, "object is required");
        //对象为true
        Assert.isTrue(true,"object must be true");
        //集合非空
        Assert.notEmpty(new HashMap<>(), "collection must not be empty");
        //字符不为null且字符长度不为0
        Assert.hasLength(new String("1"), "text must be specified");
        //不为null且必须至少包含一个非空格的字符
        Assert.hasText(new String("1"), "text must not be empty");
        //obj必须能被正确造型成为clazz指定的类
        Assert.isInstanceOf(Map.class, object, "clazz must be of type [clazz]");
    }

}
