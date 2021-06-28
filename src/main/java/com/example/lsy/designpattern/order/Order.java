package com.example.lsy.designpattern.order;
import java.util.stream.Stream;

public class Order {
    public static void main(String[] args) {
        Light light = new Light();
        Door door = new Door();
        Computer computer = new Computer();
        /**先初始化一下*/
        Control control = new Control();
        control.setCommand(0,new LightOn(light));
        control.setCommand(1,new LightOff(light));
        control.setCommand(2,new ComputeOn(computer));
        control.setCommand(3,new ComputeOff(computer));
        control.setCommand(4,new DoorOpen(door));
        control.setCommand(5,new DoorClose(door));

        control.keyPressed(0);
        control.keyPressed(1);
        control.keyPressed(2);
        control.keyPressed(3);
        control.keyPressed(4);
        control.keyPressed(5);

        /**更快捷的一键调用接口*/
        QuickCommand quickCommand = new QuickCommand(new Command[] {
                new DoorClose(door),new LightOff(light),new ComputeOn(computer)});
        System.out.println("开始一键调用了");
        control.setCommand(8,quickCommand);
        control.keyPressed(8);
    }
}

class Control {
    private static final int CONTROL = 9;
    private Command[] commands;
    public Control() {
        commands = new Command[CONTROL];
        Stream.of(commands).forEach(v->v = new NoCommand());
    }

    public void setCommand(int index ,Command command) {
        commands[index] = command;
    }

    public void keyPressed(int index) {
        commands[index].execute();
    }
}

class NoCommand implements Command{
    public void execute() {}
}

class QuickCommand implements Command {

    private Command[] commands;

    public QuickCommand(Command[] commands) {
        this.commands = commands;
    }

    public void execute() {
        Stream.of(commands).forEach(v->v.execute());
    }
}

class Door {
    public void open(){
        System.out.println("开门");
    }
    public void close(){
        System.out.println("关门");
    }
}

class Light {
    public void on(){
        System.out.println("开灯");
    }
    public void off(){
        System.out.println("关灯");
    }
}

class Computer {
    public void on(){
        System.out.println("开电脑");
    }
    public void off(){
        System.out.println("关电脑");
    }
}

/**
 * 封装起来
 */
interface Command {
    public void execute();
}

class LightOff implements Command {

    private Light light;

    public LightOff(Light light) {
        this.light = light;
    }

    public void execute() {
        light.off();
    }
}

class LightOn implements Command {

    private Light light;

    public LightOn(Light light) {
        this.light = light;
    }

    public void execute() {
        light.on();
    }
}

class ComputeOff implements Command {

    private Computer computer;

    public ComputeOff(Computer computer) {
        this.computer = computer;
    }

    public void execute() {
        computer.off();
    }
}

class ComputeOn implements Command {

    private Computer computer;

    public ComputeOn(Computer computer) {
        this.computer = computer;
    }

    public void execute() {
        computer.on();
    }
}

class DoorOpen implements Command {

    private Door door;

    public DoorOpen(Door door) {
        this.door = door;
    }

    public void execute() {
        door.open();
    }
}

class DoorClose implements Command {

    private Door door;

    public DoorClose(Door door) {
        this.door = door;
    }

    public void execute() {
        door.close();
    }
}


