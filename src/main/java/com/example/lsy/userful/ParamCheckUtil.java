package com.example.lsy.userful;

import org.apache.commons.lang.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ParamCheckUtil<T> {
    /**
     * 之前原始的值
     */
    private T original;
    /**
     * 当前的值
     */
    private T current;

    public ParamCheckUtil() {
    }

    public ParamCheckUtil(T current, T original) {
        this.original = original;
        this.current = current;
    }

    public T getOriginal() {
        return original;
    }


    public void setOriginal(T original) {
        this.original = original;
    }


    public T getCurrent() {
        return current;
    }


    public void setCurrent(T current) {
        this.current = current;
    }


    public Boolean contrastObj(Class<T> cls) {

        boolean isEqual = true;
        try {
            //Class clazz = this.original.getClass();
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), cls);
                Method getMethod = pd.getReadMethod();
                Object o1 = getMethod.invoke(this.original);
                Object o2 = getMethod.invoke(this.current);
                String s1 = o1 == null ? "" : o1.toString();//避免空指针异常  
                String s2 = o2 == null ? "" : o2.toString();//避免空指针异常  
                //思考下面注释的这一行：会bug的，虽然被try catch了，程序没报错，但是结果不是我们想要的  
                //if (!o1.toString().equals(o2.toString())) {  
                if (!StringUtils.isEmpty(s2) && !s1.equals(s2)) {
                    isEqual = false;
                    System.out.println("不一样的属性：" + field.getName() + " 属性值：[" + s1 + "," + s2 + "]");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return isEqual;
    }
}