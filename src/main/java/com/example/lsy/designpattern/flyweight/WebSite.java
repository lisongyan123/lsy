package com.example.lsy.designpattern.flyweight;

import java.util.HashMap;

public abstract class WebSite {
    public abstract void use();

    public static void main(String[] args) {
        WebSiteFactory factory = new WebSiteFactory();

        WebSite fx = factory.get("产品展示");
        fx.use();

        WebSite fy = factory.get("产品展示");
        fy.use();

        WebSite fz = factory.get("产品展示");
        fz.use();

        WebSite fa = factory.get("博客");
        fa.use();

        WebSite fb = factory.get("博客");
        fb.use();

        WebSite fc = factory.get("博客");
        fc.use();

        System.out.println("网站分类总数为：" + factory.getWebSiteCount());
    }
}


class ConcreteWebSite extends WebSite {

    private String name = "";

    public ConcreteWebSite(String name) {
        this.name = name;
    }

    @Override
    public void use() {
        System.out.print("网站ConcreteWebSite " + name);
    }
}

class WebSiteFactory {
    private HashMap<String,ConcreteWebSite> map = new HashMap<>();

    public WebSite get(String key) {
        if(!map.containsKey(key)){
            System.out.print("\n 内存中不存在");
            map.put(key,new ConcreteWebSite(key));
        }
        return (WebSite)map.get(key);
    }

    public int getWebSiteCount() {
        return map.size();
    }
}

