package com.example.lsy.designpattern.adapter;

interface Inter1 {
    void func1();
}

interface Inter2 {
    void func2();
}

class Impl1 implements Inter1 {

    public void func1() {
        System.out.println("方法一执行");
    }

}

class Impl2 extends Impl1 implements Inter2 {

    public void func2() {
        System.out.println("方法二执行,包含了方法1");
        func1();
    }

}

public class AdapterIncludeTest {

    public static void main(String[] args) {
        Inter2 inter2 = new Impl2();
        inter2.func2();
    }

}
