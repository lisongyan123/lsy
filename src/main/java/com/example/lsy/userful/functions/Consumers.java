package com.example.lsy.userful.functions;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 *  Conseumer和Function区别就是 Consumer返回值类型是void
 */
public class Consumers {
    /**
     * 1
     * 2
     * 3
     * 4
     * 5
     * 6
     * 7
     * 8
     * ===========
     * 1
     * 2
     * 3
     * 4
     * 5
     * 6
     * 7
     * 8
     * ***********BiConsumer***********
     * 3
     * Hello!Cattle
     *
     * Process finished with exit code 0
     * @param args
     */
    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        Consumer<Integer> integerConsumer = x -> System.out.println(x);
        forEach(integers, integerConsumer);
        System.out.println("===========");
        forEach(integers, x -> System.out.println(x));

        System.out.println("***********BiConsumer***********");
        add(1, 2, (x, y) -> System.out.println(x + y));
        add("Hello!", "Cattle", (x, y) -> System.out.println(x + y));
    }

    static <T> void forEach(List<T> list, Consumer<T> consumer) {
        list.forEach(consumer);
    }

    static <T> void add(T a, T b, BiConsumer<T, T> c) {
        c.accept(a, b);
    }
}
