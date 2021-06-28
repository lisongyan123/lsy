package com.example.lsy.userful.functions;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

/**
 * java8的一些接口理解
 */
public class JavaFunctions<T> implements Collector<T, List<T>, List<T>> {

    /**
     * supplier就是新建一个对象 只有调用get的时候才会从内存加载,出参<T>
     * @return
     */
    @Override
    public Supplier<List<T>> supplier() {
        return () -> new ArrayList<T>();
    }

    /**
     * 入参<T>
     * @return
     */
    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return (list, item) -> list.add(item);
    }

    /**
     * 入参R 出参 T
     * @return
     */
    @Override
    public Function<List<T>, List<T>> finisher() {
        return i -> i;
    }

    /**
     * BinaryOperator返回一个T类型的数据
     * min和max
     */
    @Override
    public BinaryOperator<List<T>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, CONCURRENT));
    }

}
