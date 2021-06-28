package com.example.lsy.designpattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**编写动态处理器，实现 InvocationHandler */
public class DynamicProxy implements InvocationHandler {

    private Object object;

    public DynamicProxy(final Object objcet) {
        this.object = objcet;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("买房前，996，职场pua，修福报，天天刷hard题，面头条，怼hr，攒够钱");
        Object result = method.invoke(object,args);
        System.out.println("买完了，结果被裁员了，一个月房贷2w，开始吃土");
        return result;

    }

    public static void main(String[] args){
        BuyHouse buyHouse = new ProxyHandler();
        BuyHouse dynamicProxy = (BuyHouse) Proxy.newProxyInstance(BuyHouse.class.getClassLoader(),
                new Class[]{BuyHouse.class}, new DynamicProxy(buyHouse));
        dynamicProxy.buyHouse();
    }
}

