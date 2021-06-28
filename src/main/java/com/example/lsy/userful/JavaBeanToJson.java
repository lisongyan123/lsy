package com.example.lsy.userful;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

public class JavaBeanToJson {
 
    private static <T> Object getBean(T object) throws Exception {
        Class<?> classType = object.getClass();
        //SqlRowSet srs = jdbcTemplate.queryForRowSet(sql);
        Field[] fields = classType.getDeclaredFields();//得到对象中的字段
        //每次循环时，重新实例化一个与传过来的对象类型一样的对象
        T objectCopy = (T) classType.getConstructor(new Class[]{}).newInstance(new Object[]{});
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            Object value = null;

            //根据字段类型决定结果集中使用哪种get方法从数据中取到数据
            if (field.getType().equals(String.class)) {
                value = "";
            } else if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
                value = new Integer(0);
            } else if (field.getType().equals(double.class) || field.getType().equals(Double.class)) {
                value = new Double(0);
            } else if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
                value = new Long(0);
            } else if (field.getType().equals(Date.class)) {
                value = new Date();
            } else if (field.getType().equals(Object.class)) {
                value = new Object();
            }
            // 获得属性的首字母并转换为大写，与setXXX对应
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String setMethodName = "set" + firstLetter
                    + fieldName.substring(1);
            if (fieldName.equals("serialVersionUID")) {
                continue;
            }
            Method setMethod = classType.getMethod(setMethodName,
                    new Class[]{field.getType()});
            setMethod.invoke(objectCopy, new Object[]{value});//调用对象的setXXX方法
        }
 
        return objectCopy;
    }
}