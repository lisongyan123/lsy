package com.example.lsy.designpattern.component;

/**
 * 如果不想增加很多子类扩展功能时，动态添加或撤销一个功能时候
 */
public class Component {
    public static void main(String[] args) {
        Operation operation = new ConcretateCompent();
        operation = new Decorator2(operation);
        operation = new Decorator3(operation);
        operation.operation();
    }
}

abstract class Operation{
     public abstract void operation();
}

class ConcretateCompent extends Operation {
    @Override
    public void operation() {
        System.out.print("执行ConcretateCompent");
    }
}

class Decorator extends Operation {

    private Operation operation;

    public Decorator(Operation operation) {
        this.operation = operation;
    }

    @Override
    public void operation() {
        System.out.print("执行Decorator");
    }
}

class Decorator2 extends Decorator {
    public Decorator2(Operation operation) {
        super(operation);
    }

    private void method() {
        System.out.print("decorator2");
    }

    public void operation(){
        this.method();
        super.operation();
    }
}

class Decorator3 extends Decorator {

    //定义被修饰者
    public Decorator3(Operation operation) {
        super(operation);
    }

    //定义自己的修饰方法
    private void method2() {
        System.out.println("method2 修饰");
    }

    public void operation() {
        super.operation();
        this.method2();
    }
}