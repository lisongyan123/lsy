package com.example.lsy.userful;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
public class CompareWith1 {
    public static void main(String[] args) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        List<A> a = Arrays.asList(
                new A("1", "good", "arg1", "arg2", null, null),
                new A("1", "good", "arg1", "arg2", null, null),
                new A("2", "good", "arg1", "arg2", null, null),
                new A("2", "good", "arg1", "arg2", null, null),
                new A("3", "good", "arg1", "arg2", null, null),
                new A("1", "good1", "arg1", "arg2", null, null)
        );
        Map<Object, List<A>> map = a.stream().collect(Collectors.groupingBy(A::getId));
        map.entrySet().stream().forEach(v -> {
            Long count1 = v.getValue().stream().map(k -> k.getName()).distinct().count();
            Long count2 = v.getValue().stream().map(k -> k.getArg2()).distinct().count();
            v.getValue().stream().forEach(k -> {
                k.setNum1(count1);
                k.setNum2(count2);
            });
        });
//        List list = map.entrySet().stream().filter(distinctByKey(A::getArg1)).collect(Collectors.toList());
        a = a.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()
                -> new TreeSet<>(Comparator.comparing(A::getId))), ArrayList::new));
        //[{"arg1":"arg1","arg2":"arg2","id":"1","name":"good","num1":2,"num2":1},
        // {"arg1":"arg1","arg2":"arg2","id":"2","name":"good","num1":1,"num2":1},
        // {"arg1":"arg1","arg2":"arg2","id":"3","name":"good","num1":1,"num2":1}]
        System.out.println("*****************");
        //用于比较两个属性是否相等
        A ass1 = new A("1", "good", "arg1", null, null, null);
        A ass2 = new A("1", "good", "arg1", "arg2", null, null);
        Boolean istrue = new ParamCheckUtil<A>(ass1, ass2).contrastObj(A.class);
        //true
        //ass1:{"name":"good","arg2":"arg2","id":"1","arg1":"arg1"}
        //true
        Boolean s = !false ^ false;

        String arg2 = ass1.getArg2();
        System.out.println("arg2:" + arg2);

        A ass3 = new A();
        BeanUtils.copyProperties(ass1,ass3);
    }

    public static String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    public static boolean getTrue(Object obj) throws NoSuchFieldException, IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) { //根据入参循环取出对应类的属性名及值
            String fieldName = field.getName();//获取的字段名
            Field fieldParam = obj.getClass().getDeclaredField(fieldName);
            fieldParam.setAccessible(true);//设置对象的访问权限，保证private属性的访问权限
            String value = (String) fieldParam.get(obj);//获取字段名对应的值
            System.out.println(value);
        }
        return true;
    }


    public static void printClassMessage(Object obj) {

        Class c = obj.getClass();

        //获取类名
        System.out.println("类的名称是：" + c.getName());

        //获取方法
        Method[] ms = c.getMethods();

        for (int i = 0; i < ms.length; i++) {

            //获取方法返回值类型
            Class returnType = ms[i].getReturnType();
            System.out.print(returnType.getName() + " ");
            //得到方法的名称
            System.out.print(ms[i].getName() + "(");
            //获取参数类型
            Class[] paramTypes = ms[i].getParameterTypes();
            for (Class class1 : paramTypes) {
                System.out.print(class1.getName() + ",");
            }
            System.out.println(")");

        }
    }


    private static <T> Predicate<T> distinctByKey(Supplier keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.get(), Boolean.TRUE) == null;
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
}


