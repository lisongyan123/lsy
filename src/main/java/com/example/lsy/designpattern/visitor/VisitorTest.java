package com.example.lsy.designpattern.visitor;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/***
 * 增加访问者非常容易
 */
public class VisitorTest {
    private List<Staff> staffList = new LinkedList<>();

    public VisitorTest() {
        staffList.add(new Manager("经理春泽"));
        staffList.add(new Manager("经理新民"));
        staffList.add(new Engineer("工程师-A"));
        staffList.add(new Engineer("工程师-B"));
        staffList.add(new Engineer("工程师-C"));
    }
    public void showReport(Visitor visitor) {
        staffList.stream().forEach(v->v.accept(visitor));
    }

    public static void main(String[] args) {
        System.out.print("=========CEO========");
        VisitorTest visitorTest = new VisitorTest();
        visitorTest.showReport(new CEOVisitor());
        System.out.print("=========CTO========");
        visitorTest.showReport(new CTOVisitor());
    }
}

interface Visitor {

    // 访问工程师类型
    void visit(Engineer engineer);

    // 访问经理类型
    void visit(Manager manager);
}

class CEOVisitor implements Visitor {

    @Override
    public void visit(Engineer engineer) {
        System.out.println("工程师: " + engineer.name + ", KPI: " + engineer.kpi);
    }

    @Override
    public void visit(Manager manager) {
        System.out.println("经理: " + manager.name + ", KPI: " + manager.kpi + ", 新产品数量: " + manager.getProducts());
    }
}

class CTOVisitor implements Visitor {

    @Override
    public void visit(Engineer engineer) {
        System.out.println("工程师: " + engineer.name + ", 代码行数: " + engineer.getCodeLines());
    }

    @Override
    public void visit(Manager manager) {
        System.out.println("经理: " + manager.name + ", 产品数量: " + manager.getProducts());
    }
}

// 员工基类
abstract class Staff {

    public String name;
    public int kpi;// 员工KPI

    public Staff(String name) {
        this.name = name;
        kpi = new Random().nextInt(10);
    }
    // 核心方法，接受Visitor的访问
    public abstract void accept(Visitor visitor);
}

class Engineer extends Staff {
    public Engineer(String name) {
        super(name);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getCodeLines() {
        return new Random().nextInt(10*10000);
    }
}

class Manager extends Staff {
    public Manager(String name) {
        super(name);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getProducts() {
        return new Random().nextInt(10);
    }
}