package com.example.lsy.designpattern.template;

public class Template {
    public static void main(String[] args) {
        Worker itWorker = new ItWorker("王永梅");
        itWorker.workOneDay();
        Worker hr = new SbHrWorker("张随");
        hr.workOneDay();
        Worker manager = new ManagerWorker("春泽");
        manager.workOneDay();
    }
}

abstract class Worker {
    protected String name;
    public Worker(String name) {
        this.name = name;
    }

    public final void workOneDay(){
        System.out.println("=========work start =========");
        enterCompany();
        conputerOn();
        work();
        computerOff();
        exitCompany();
        System.out.println("=========work end =========");
    }

    public abstract void work();

    public void computerOff() {
        System.out.println("关闭电脑");
    }
    public void conputerOn() {
        System.out.println("打开电脑");
    }
    public void enterCompany() {
        System.out.println("进入公司");
    }
    public void exitCompany() {
        System.out.println("退出公司");
    }
}

class ItWorker extends Worker {

    public ItWorker(String name) {
        super(name);
    }

    @Override
    public void work() {
        System.out.println(name + "写程序 + 写bug");
    }
}

class SbHrWorker extends Worker {

    public SbHrWorker(String name) {
        super(name);
    }

    @Override
    public void work() {
        System.out.println(name + "压工资，骗候选人，贱嗖嗖的，掌握杀生大权");
    }
}

class ManagerWorker extends Worker {

    public ManagerWorker(String name) {
        super(name);
    }

    @Override
    public void work() {
        System.out.println(name + "天天催进度，啥活也不干");
    }
}