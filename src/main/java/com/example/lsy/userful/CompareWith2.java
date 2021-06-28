package com.example.lsy.userful;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

public class CompareWith2 {

    public static void main(String[] args) throws Exception {
        A ass1 = new A("1", "good", "arg1", "arg2",1l, 2l);
        Method method1 = BeanUtils.findDeclaredMethod(A.class, "getId");
        Object obj1 = method1.invoke(ass1);
        System.out.println("obj1:" + obj1);
        //obj1:1
        PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(A.class, "id");
        System.out.println(pd);
        Method getMethod = pd.getReadMethod();
        Object obj2 = getMethod.invoke(ass1);
        System.out.println("obj2:" + obj2);
//        obj2:1
        A ass2 = new A();
        ass2 = (A) getBean(ass2);

    }

    static class A {
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        String id;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        String name;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        String arg1;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        String arg2;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        Long num1;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        Long num2;

        public A() {
        }

        public A(String id, String name, String arg1, String arg2, Long num1, Long num2) {
            this.id = id;
            this.name = name;
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.num1 = num1;
            this.num2 = num2;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getArg1() {
            return arg1;
        }

        public void setArg1(String arg1) {
            this.arg1 = arg1;
        }

        public String getArg2() {
            return arg2;
        }

        public void setArg2(String arg2) {
            this.arg2 = arg2;
        }

        public Long getNum1() {
            return num1;
        }

        public void setNum1(Long num1) {
            this.num1 = num1;
        }

        public Long getNum2() {
            return num2;
        }

        public void setNum2(Long num2) {
            this.num2 = num2;
        }

    }

    //属性设置默认值
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
            if(field.getType().getName().endsWith("Long"))
                System.out.println("field name is:" + field.getName());
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
