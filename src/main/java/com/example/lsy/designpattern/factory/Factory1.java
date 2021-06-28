package com.example.lsy.designpattern.factory;

/**
 * 抽象产品类
 */
abstract class AbstractProduct1 {

    public void product1() {}

    public abstract void todo();

}

abstract class AbstractProduct2 {

    public void product2() {}

    public abstract void todo();

}

class ProductA extends AbstractProduct1 {
    @Override
    public void todo() {
        System.out.println("我是工厂1的产品A");
    }
}

class ProductB extends AbstractProduct1 {
    @Override
    public void todo() {
        System.out.println("我是工厂1的产品B");
    }
}

class ProductC extends AbstractProduct2 {
    @Override
    public void todo() {
        System.out.println("我是工厂2的产品C");
    }
}

class ProductD extends AbstractProduct2 {
    @Override
    public void todo() {
        System.out.println("我是工厂2的产品D");
    }
}

/**
 * 抽象工厂类
 */
abstract class AbstractCreator {

    abstract AbstractProduct1 abstractProduct1();
    abstract AbstractProduct2 abstractProduct2();

}

class Creator1 extends AbstractCreator {

    @Override
    AbstractProduct1 abstractProduct1() {
        return new ProductA();
    }

    @Override
    AbstractProduct2 abstractProduct2() {
        return new ProductC();
    }
}

class Creator2 extends AbstractCreator {

    @Override
    AbstractProduct1 abstractProduct1() {
        return new ProductB();
    }

    @Override
    AbstractProduct2 abstractProduct2() {
        return new ProductD();
    }
}

public class Factory1 {
    public static void main(String[] args) {
        /**定义两个工厂*/
        AbstractCreator creator1 = new Creator1();
        AbstractCreator creator2 = new Creator2();
        /**生产ABCD对象,先实例化*/
        AbstractProduct1 A = creator1.abstractProduct1();
        AbstractProduct1 B = creator2.abstractProduct1();
        AbstractProduct2 C = creator1.abstractProduct2();
        AbstractProduct2 D = creator2.abstractProduct2();
        /**实例化后调用方法*/
        A.todo();
        B.todo();
        C.todo();
        D.todo();
    }
}
