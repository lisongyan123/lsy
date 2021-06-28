package com.example.lsy.userful.annotation.test;

public class User {

    @MyAnnotation(value = {"male","female"})
    public void getUser(String name, int age) {
        System.out.println("User:[" + name + "," + age + "]");
    }
}
