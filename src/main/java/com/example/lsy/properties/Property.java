package com.example.lsy.properties;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class Property {
    public static void main(String[] args) {
        try{
            Properties properties=new Properties();
            //直接写src 类路径下的文件名
            InputStream input=Property.class.getClassLoader().getResourceAsStream("application.properties");
            properties.load(input);

            //把key值转换成set 的形式，遍历set
            Enumeration<String> names=(Enumeration<String>) properties.propertyNames();
            while(names.hasMoreElements()){
                //这是key值
                String key=names.nextElement();
                String value=properties.getProperty(key);
                System.out.println(key+"="+value);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
