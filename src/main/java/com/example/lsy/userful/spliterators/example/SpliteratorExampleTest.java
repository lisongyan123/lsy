package com.example.lsy.userful.spliterators.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

public class SpliteratorExampleTest {

    public static void main(String[] args) {
        tryAdvance();
        split();
        estimateSize();
        characteristic();
    }

    /**
     * 1
     * true
     * false
     * 111
     * 112
     */
    public static void tryAdvance() {
        List<String> strings = new ArrayList<>();
        strings.add("1");
        Spliterator<String> spliterator = strings.spliterator();
        boolean flag1 =  spliterator.tryAdvance(System.out::println);
        boolean flag2 = spliterator.tryAdvance(System.out::println);
        System.out.println(flag1);
        System.out.println(flag2);

        List<String> strings2 = new ArrayList<>();
        strings2.add("111");
        strings2.add("112");
        Spliterator<String> spliterator2 = strings2.spliterator();
        spliterator2.forEachRemaining(System.out::println);
    }

    /**
     * 112
     * 111
     */
    public static void split() {
        List<String> strings = new ArrayList<>();
        strings.add("111");
        strings.add("112");
        Spliterator<String> spliterator = strings.spliterator();
        Spliterator<String> spliterator2 =  spliterator.trySplit();
        spliterator.forEachRemaining(System.out::println);
        spliterator2.forEachRemaining(System.out::println);
    }

    /**
     * 1
     */
    public static void estimateSize() {
        List<String> strings = new ArrayList<>();
        strings.add("1");
        Spliterator<String> spliterator = strings.spliterator();
        System.out.println(spliterator.estimateSize());
    }

    /**
     * ORDERED	0x00000010	16	元素之间是有顺序的
     * DISTINCT	0x00000001	1	元素之间不会重复
     * SORTED	0x00000004	4	元素遵循定义的排序顺序
     * SIZED	0x00000040	64	表示长度为有限个
     * NONNULL	0x00000100	256	表示元素不能为空
     * IMMUTABLE	0x00000400	1024	元素源不能修改(不能添加，替换或删除元素)
     * CONCURRENT	0x00001000	4096	多个线程安全同时修改元素源而无需外部同步
     * SUBSIZED	0x00004000	16384	迭代器所分割得到的子迭代器也是有序的
     */
    public static void characteristic() {
        List<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");
        Spliterator<String> spliterator = strings.spliterator();
        System.out.println(spliterator.characteristics());
        // 返回 16464 = 16384 + 64 + 16
    }
}
