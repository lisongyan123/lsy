package com.example.lsy.designpattern;

import java.util.ArrayList;
import java.util.List;

public class MediatorPattern {
    public static void main(String[] args) {
        Mediator mediator = new ConcreteMediator();
        Colleague c1 = new Colleague1();
        Colleague c2 = new Colleague2();
        mediator.register(c1);
        mediator.register(c2);
        c1.send();
        c2.send();
    }
}

abstract class Mediator {
    public abstract void register(Colleague colleague);
    public abstract void relay(Colleague cl);
}

/**中介者*/
class ConcreteMediator extends Mediator {

    List<Colleague> colleagues = new ArrayList<>();

    @Override
    public void register(Colleague colleague) {
        if (!colleagues.contains(colleague)) {
            colleagues.add(colleague);
            colleague.setMediator(this);
        }
    }

    @Override
    public void relay(Colleague cl) {
        colleagues.stream().filter(v->!v.equals(cl)).forEach(v->v.receive());
    }
}

abstract class Colleague {

    protected Mediator mediator;

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }
    public abstract void receive();
    public abstract void send();

}

class Colleague1 extends Colleague {
    public void receive(){
        System.out.print("\nColleague1收到请求");
    }
    public void send(){
        System.out.print("\nColleague1发送请求");
        mediator.relay(this);
    }
}

class Colleague2 extends Colleague {
    public void receive(){
        System.out.print("\nColleague2收到请求");
    }
    public void send(){
        System.out.print("\nColleague2发送请求");
        mediator.relay(this);
    }
}