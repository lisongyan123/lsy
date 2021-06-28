package com.example.lsy.designpattern.proxy;

public class BuyHouseImpl implements BuyHouse {

    private BuyHouse shixian;

    public BuyHouseImpl(final BuyHouse shixian){
        this.shixian = shixian;
    }

    @Override
    public void buyHouse() {
        System.out.println("买房前，996，职场pua，修福报，天天刷hard题，面头条，怼hr，攒够钱");
        shixian.buyHouse();
        System.out.println("买完了，结果被裁员了，一个月房贷2w，开始吃土");
    }

    public static void main(String[] args){
        BuyHouse proxyHandler = new ProxyHandler();
        proxyHandler.buyHouse();
        BuyHouseImpl buyHouse = new BuyHouseImpl(proxyHandler);
        buyHouse.buyHouse();
    }

}







