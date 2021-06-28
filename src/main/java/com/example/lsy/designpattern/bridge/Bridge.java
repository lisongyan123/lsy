package com.example.lsy.designpattern.bridge;

/**
 * 桥接模式适合一对多，拜托继承n层的苦恼，下图例子如果要加一个音乐播放器加一个类就行
 */
public class Bridge {
    public static void main(String[] args) {
        HandsetBrand handsetBrandA = new HandserBrandA();

        handsetBrandA.setSoft(new HandsetGame());
        handsetBrandA.run();

        handsetBrandA.setSoft(new HandsetShopping());
        handsetBrandA.run();

        HandsetBrand handsetBrandB = new HandserBrandB();

        handsetBrandB.setSoft(new HandsetGame());
        handsetBrandB.run();

        handsetBrandB.setSoft(new HandsetShopping());
        handsetBrandB.run();

    }
}

/**品牌*/
abstract class HandsetBrand {
    protected HandsetSoft soft;

    public void setSoft(HandsetSoft soft) {
        this.soft = soft;
    }

    public abstract void run();
}

/**手机软件*/
abstract class HandsetSoft {
    public abstract void run();
}

class HandserBrandA extends HandsetBrand {
    @Override
    public void run() {
        System.out.print("A品牌手机\n");
        soft.run();
    }
}

class HandserBrandB extends HandsetBrand {
    @Override
    public void run() {
        System.out.print("B品牌手机\n");
        soft.run();
    }
}

/**手机游戏*/
class HandsetGame extends HandsetSoft{
    @Override
    public void run() {
        System.out.println("执行HandsetGame方法 运行手机游戏");
    }
}

class HandsetShopping extends HandsetSoft{
    @Override
    public void run() {
        System.out.println("执行HandsetShopping方法 逛淘宝");
    }
}