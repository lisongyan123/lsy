package com.example.lsy.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

public class ObserverTest {
    public static void main(String[] args) {
        TopicImpl object = new TopicImpl();
        Observer observer1 = new Observer1(object);
        Observer observer2 = new Observer2(object);
        object.setMsg("20140420的3D号码是：127");
        object.setMsg("20140421的3D号码是：333");
    }
}

/**主题接口，所有主题必须实现此接口*/
interface Topic {
    /**注册观察者*/
    public void registerObserver(Observer observer);

    /**移除观察者*/
    public void removeObserver(Observer observer);

    /**通知所有观察者*/
    public void notifyObservers();
}

/**所有观察者必须实现此接口*/
interface Observer {
    public void update(String msg);
}

class TopicImpl implements Topic {

    private List<Observer> observers = new ArrayList<Observer>();

    /**主题更新消息*/
    public void setMsg(String msg) {
        this.msg = msg;
        notifyObservers();
    }

    private String msg;

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        int index = observers.indexOf(observer);
        if (index >= 0) {
            observers.remove(index);
        }
    }

    @Override
    public void notifyObservers() {
        observers.stream().forEach(v->v.update(msg));
    }
}

/**
 * 模拟两个观察者
 */
class Observer1 implements Observer {

    private Topic topic;

    public Observer1(Topic topic) {
        this.topic = topic;
        topic.registerObserver(this);
    }

    @Override
    public void update(String msg) {
        System.out.println("Observer1:" + msg);
    }
}

class Observer2 implements Observer {

    private Topic topic;

    public Observer2(Topic topic) {
        this.topic = topic;
        topic.registerObserver(this);
    }

    @Override
    public void update(String msg) {
        System.out.println("Observer2:" + msg);
    }
}

