package com.example.lsy.designpattern.facade;

/**
 * 外观模式：为多个复杂的子系统提供一个一致的接口，使这些子系统更加容易被访问。
 */
class Facade {

    public static void main(String[] args) {
        Facade facade = new Facade();
        facade.method();
    }

    private System1 system1 = new System1();
    private System2 system2 = new System2();
    private System3 system3 = new System3();
    public void method() {
        system1.method();
        system2.method();
        system3.method();
    }
}

class System1{
    public void method(){
        System.out.println("System1");
    }
}

class System2{
    public void method(){
        System.out.println("System2");
    }
}

class System3{
    public void method(){
        System.out.println("System3");
    }
}