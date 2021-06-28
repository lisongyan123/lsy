package com.example.lsy.designpattern.role;

/**
 * 算法族封装，可以在不修改原代码的情况下，灵活增加新算法。
 */
public class Strategy{
    public static void main(String[] args) {
        Role role = new Role();
        role.setAttack(new AttackImpl());
        role.setDefend(new DefendImpl());
        role.setDisplay(new DisplayImpl());
        role.setRun(new RunImpl());
        role.run();
        role.attack();
        role.defend();
        role.attack();
    }
}

interface Attack {
    void attack();
}

interface Defend {
    void defend();
}

interface Display {
    void display();
}

interface Run {
    void run();
}

class AttackImpl implements Attack {
    @Override
    public void attack() {
        System.out.println("attack");
    }
}

class DefendImpl implements Defend {
    @Override
    public void defend() {
        System.out.println("defend");
    }
}

class DisplayImpl implements Display {
    @Override
    public void display() {
        System.out.println("display");
    }
}

class RunImpl implements Run {
    @Override
    public void run() {
        System.out.println("run");
    }
}

class Role {
    protected String name;
    protected Run run;
    protected Defend defend;
    protected Display display;
    protected Attack attack;

    public void setRun(Run run) {
        this.run = run;
    }

    public void setDefend(Defend defend) {
        this.defend = defend;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public void setAttack(Attack attack) {
        this.attack = attack;
    }

    public Run getRun() {
        return run;
    }

    public Defend getDefend() {
        return defend;
    }

    public Display getDisplay() {
        return display;
    }

    public Attack getAttack() {
        return attack;
    }

    protected void display() {
        display.display();
    }

    protected void defend() {
        defend.defend();
    }

    protected void run() {
        run.run();
    }

    protected void attack() {
        attack.attack();
    }
}


