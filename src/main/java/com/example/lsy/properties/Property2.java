package com.example.lsy.properties;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Property2 {
    public static void main(String[] args) {

        try {
            //处理成编码样式。
            Properties properties = new Properties();

            //多添加几个值。
            properties.setProperty("name", "两个蝴蝶飞");
            properties.setProperty("sex", "男");
            OutputStream output = new FileOutputStream("D:\\jdbc.xml");
            //编码设置成utf-8的形式。
            properties.storeToXML(output, "填充到xml", "utf-8");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
