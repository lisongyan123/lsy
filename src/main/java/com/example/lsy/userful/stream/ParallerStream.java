package com.example.lsy.userful.stream;

import org.assertj.core.util.Lists;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallerStream {
    /**
     * 实验证明 线程池设置为15的时候是最快的
     *
     * @param args
     */
    public static void main(String[] args) {
//        test();
//        test1();
//        test2();
//        test3();
        test4();
    }

    public static void test() {
        Instant start = Instant.now();
        long sum = LongStream.rangeClosed(0, 10000000000L).parallel().sum();
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("parallel耗费时间" + Duration.between(start, end).toMillis());
    }

    public static void test1() {
        Instant start = Instant.now();
        long sum = 0L;
        for (long i = 0L; i < 10000000000L; i++) {
            sum += i;
        }
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("for耗费时间" + Duration.between(start, end).toMillis());
    }

    public static void test2() {
        Instant start = Instant.now();
        long sum = LongStream.rangeClosed(0, 10000000000L).sum();
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("Stream耗费时间" + Duration.between(start, end).toMillis());
    }

    public static void test3() {
        ForkJoinPool forkJoinPool = new ForkJoinPool(15);
        Instant start = Instant.now();
        forkJoinPool.submit(() -> {
            long sum = LongStream.rangeClosed(0, 10000000000L).parallel().sum();
            System.out.println(sum);
        }).join();
        Instant end = Instant.now();
        System.out.println("parallel耗费时间" + Duration.between(start, end).toMillis());
    }

    /**
     * parallelStream 当执行任务比较长时候要比串行的快一些
     */
    public static void test4() {
        //创建集合大小为100
        List<Integer> integers = Lists.newArrayList();
        for (int i = 0; i < 100; i++){
            integers.add(i);
        }
        //多管道遍历
        List<Integer> integerList = Collections.synchronizedList(new ArrayList<>());
        Instant start = Instant.now();
        integers.parallelStream().forEach(e -> {
            //添加list的方法
            integerList.add(e);
            try {
                //休眠100ms，假装执行某些任务
                Thread.sleep(1);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });
        Instant end = Instant.now();
        System.out.println("parallel耗费时间" + Duration.between(start, end).toMillis());
        System.out.println(integerList.toString() + "\n" + integerList.size());

        List<Integer> list = new ArrayList<>();
        start = Instant.now();
        integers.stream().forEach(e -> {
            //添加list的方法
            list.add(e);
            try {
                //休眠100ms，假装执行某些任务
                Thread.sleep(1);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });
        end = Instant.now();
        System.out.println("stream耗费时间" + Duration.between(start, end).toMillis());
        System.out.println(list.toString() + "\n" + list.size());
    }
}
