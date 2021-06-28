package com.example.lsy.designpattern.observer;

import java.util.Observable;

class Topic2 extends Observable {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
        /**Observable自带的方法*/
        setChanged();
        notifyObservers();
    }
}

class ObserverImpl2 extends Observable {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
        /**Observable自带的方法*/
        setChanged();
        notifyObservers();
    }
}

class Observer3 implements java.util.Observer {

    public void registerSubject(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Topic2) {
            Topic2 topic2 = (Topic2) o;
            System.out.println("Observer3 msg:" + topic2.getMsg());
        }
        if(o instanceof ObserverImpl2) {
            ObserverImpl2 observerImpl2 = (ObserverImpl2) o;
            System.out.println("ObserverImpl2 msg:" + observerImpl2.getMsg());
        }
    }

}

public class ObserverTest2 {
    public static void main(String[] args) {
        Topic2 topic2 = new Topic2();
        ObserverImpl2 observerImpl2 = new ObserverImpl2();

        Observer3 observer3 = new Observer3();
        observer3.registerSubject(topic2);
        observer3.registerSubject(observerImpl2);

        topic2.setMsg("我是topic2 ：110");
        observerImpl2.setMsg("我是observerImpl2 ：220");
    }
}
