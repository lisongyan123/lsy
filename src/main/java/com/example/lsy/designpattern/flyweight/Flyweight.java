package com.example.lsy.designpattern.flyweight;

import java.util.HashMap;

/**
 * 享元模式 每次创建字符串 加大内存开销
 * 可以解决共享内存空间问题
 */
public class Flyweight {
    private static HashMap<String,Shared> map = new HashMap();
    /**从内存取出对象*/
    public static Shared getFlyWeight(String extrinsic) {
        Shared share;
        if(map.containsKey(extrinsic)){
            share = map.get(extrinsic);
            System.out.print("已有" + extrinsic + "从内存取出 \n");
        } else {
            share = new AbleShared(extrinsic);
            map.put(extrinsic,share);
            System.out.print("创建" + extrinsic + "并从内存取出 \n");
        }
        return share;
    }

    public static void main(String[] args) {
        int extrinsic = 22;
        Shared shared1 = getFlyWeight("X");
        shared1.operate(++ extrinsic);

        Shared shared2 = getFlyWeight("Y");
        shared2.operate(++ extrinsic);

        Shared shared3 = getFlyWeight("Z");
        shared3.operate(++ extrinsic);

        Shared shared4 = getFlyWeight("Q");
        shared4.operate(++ extrinsic);

        Shared unshare = new UnableShared("unshare");
        unshare.operate(++ extrinsic);

        Shared shared5 = getFlyWeight("X");
        shared4.operate(++ extrinsic);
    }
}

abstract class Shared {
    /**内部*/
    public String intrinsic;
    /**外部*/
    public String extrinsic;

    public Shared(String extrinsic) {
        this.extrinsic = extrinsic;
    }

    public abstract void operate(int extrinsic);

    public String getIntrinsic() {
        return intrinsic;
    }

    public void setIntrinsic(String intrinsic) {
        this.intrinsic = intrinsic;
    }
}

class AbleShared extends Shared {

    public AbleShared(String extrinsic) {
        super(extrinsic);
    }

    @Override
    public void operate(int extrinsic) {
        System.out.print("\n AbleShared" + extrinsic);
    }
}

class UnableShared extends Shared {
    public UnableShared(String extrinsic) {
        super(extrinsic);
    }

    @Override
    public void operate(int extrinsic) {
        System.out.print("\n UnableShared" + extrinsic);
    }
}