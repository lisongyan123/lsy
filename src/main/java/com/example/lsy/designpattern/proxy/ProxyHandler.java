package com.example.lsy.designpattern.proxy;

/**代理实现类*/
public class ProxyHandler implements  BuyHouse{

    @Override
    public void buyHouse() {
        System.out.println("我要买房子，北京的房子贵，我就买老家的房子");
    }

}

