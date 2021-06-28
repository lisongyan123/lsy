package com.example.lsy.designpattern;

/**
 * 在应用场景中，某类只要求生成一个对象的时候，如一个班中的班长、每个人的身份证号等。
 * 当对象需要被共享的场合。由于单例模式只允许创建一个对象，共享该对象可以节省内存，并加快对象访问速度。如 Web 中的配置对象、数据库的连接池等。
 * 当某类需要频繁实例化，而创建的对象又频繁被销毁的时候，如多线程的线程池、网络连接池等。
 */
public class Singleton {

    /**静态内部类*/
    private static class SingletonHolder{
        public static Singleton instance = new Singleton();
    }

    private Singleton(){}

    public static Singleton newInstance(){
        return SingletonHolder.instance;
    }

    /**枚举,它不仅能避免多线程同步问题，而且还能防止反序列化重新创建新的对象*/
    public enum EnumSingleton{
        instace;
        public void getInstace(){}
    }

    /**应用 , 川普当选总统*/
    static class President{

        public static President president = new President();

        private President(){
            System.out.println("新总统 ， 川普大王 ，特朗普");
        }

        public static President getPresident(){
            return president;
        }
    }

    public static void main(String[] args){
        President president1 ;
        president1 = President.getPresident();
        President president2 = President.getPresident();
    }
}
