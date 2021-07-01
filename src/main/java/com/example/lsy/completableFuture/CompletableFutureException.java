package com.example.lsy.completableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * @title： exceptionally 和 hander区别是 后者收集异常和返回结果 异常处理用handle
 * @author: lisongyan@fang.com
 * @date: 2021年06月28日 12:35
 * @description:
 */
public class CompletableFutureException {
    private static final Logger logger = LoggerFactory.getLogger(CompletableFutureException.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
//        test();
//        exceptionHandler();
//        test1();
//        write();
    }


    public static void write() throws IOException {
        String content = "Hello World !!";
        Path pathV1 = Paths.get("v1.json");
        Path pathV2 = Paths.get("v2.json");
        if (!Files.exists(pathV1)) {
            Files.createFile(pathV1);
        }
        if (!Files.exists(pathV2)) {
            Files.createFile(pathV2);
        }
        Files.write(pathV1, content.getBytes());
        Files.write(pathV2, content.getBytes());
    }

    public static void test1() throws ExecutionException, InterruptedException {
        List<Throwable> collectedExceptions = Collections.synchronizedList(new ArrayList<>());
        CompletableFuture<Void> process1 = CompletableFuture.runAsync(() -> {
            System.out.println("Process 1 with exception");
            throw new RuntimeException("Exception 1");
        }).exceptionally(exception -> {
            // Handle your exception here
            collectedExceptions.add(exception);
            return null;
        });

        CompletableFuture<Void> process2 = CompletableFuture.runAsync(() -> {
            System.out.println("Process 2 without exception");
        });

        CompletableFuture<Void> process3 = CompletableFuture.runAsync(() -> {
            System.out.println("Process 3 with exception");
            throw new RuntimeException("Exception 3");
        }).exceptionally(exception -> {
            // Handle your exception here
            collectedExceptions.add(exception);
            return null;
        });

        CompletableFuture<Void> allOfProcesses = CompletableFuture.allOf(process1, process2, process3);

        allOfProcesses.get();
    }

    public static void test() {
        List<String> resultList = Collections.synchronizedList(new ArrayList<>(3));
        Map<String, String> errorMap = new ConcurrentHashMap<>(0);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                if (1 == 1) throw new RuntimeException("aaa");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "future1";
        }).handle((res, exception) -> {
            if (exception == null) {
                resultList.add(res.toString());
            } else {
                errorMap.put("future1", exception.getMessage());
            }
            return "";
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
//                if (1 == 1) throw new RuntimeException("bbb");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "future2";
        }).handle(new BiFunction<String, Throwable, String>() {
            @Override
            public String apply(String s, Throwable throwable) {
                if (throwable == null) {
                    resultList.add(s.toString());
                } else {
                    errorMap.put("future2", throwable.getMessage());
                }
                return "";
            }
        });
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
//                if (1 == 1) throw new RuntimeException("ccc");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "future3";
        }).handle(new BiFunction<String, Throwable, String>() {
            @Override
            public String apply(String s, Throwable throwable) {
                if (throwable == null) {
                    resultList.add(s.toString());
                } else {
                    errorMap.put("future3", throwable.getMessage());
                }
                return "";
            }
        });

        CompletableFuture.allOf(
                CompletableFutureHandleTimeout.completeOnTimeout("future1 执行超时", future1, 2, TimeUnit.SECONDS),
                CompletableFutureHandleTimeout.completeOnTimeout("future2 执行超时", future2, 2, TimeUnit.SECONDS),
                CompletableFutureHandleTimeout.completeOnTimeout("future3 执行超时", future3, 2, TimeUnit.SECONDS)
        ).whenCompleteAsync(
                (v, th) -> {
                    logger.info("所有任务执行完成触发\n" + "返回结果集合>>> resultList：【{}】，\n异常集合>>>errorMap：【{}】", resultList, errorMap);
                }
        ).join();
//
//        CompletableFuture[] completableFutures = new CompletableFuture[]{
//                CompletableFutureHandleTimeout.completeOnTimeout("future1 执行超时", future1, 2, TimeUnit.SECONDS),
//                CompletableFutureHandleTimeout.completeOnTimeout("future2 执行超时", future2, 2, TimeUnit.SECONDS),
//                CompletableFutureHandleTimeout.completeOnTimeout("future3 执行超时", future3, 2, TimeUnit.SECONDS),
//        };
//
//        CompletableFuture
//                .allOf(completableFutures)
//                .whenComplete(
//                        (v, th) -> {
//                            System.out.println("所有任务执行完成触发\n resultList=" + resultList + "\n errorMap=" + errorMap);
//                        }
//                ).join();
        executor.shutdown();
    }

    public static void exceptionHandler() {
        //记录开始时间
        Long start = System.currentTimeMillis();

        //定长10线程池
        ExecutorService executor = Executors.newFixedThreadPool(3);

        //任务
        List<Integer> taskList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<String> resultList = new ArrayList<>();

        Map<String, String> errorMap = new HashMap<>();

        Stream<CompletableFuture<String>> completableFutureStream = taskList.stream()
                .map(num -> {
                            return CompletableFuture
                                    .supplyAsync(() -> {
                                                return getDouble(num);
                                            },
                                            executor)
                                    .handle(new BiFunction<Integer, Throwable, String>() {
                                        @Override
                                        public String apply(Integer s, Throwable throwable) {
                                            if (throwable == null) {
                                                System.out.println("任务" + num + "完成! result=" + s + ", " + new Date());
                                                resultList.add(s.toString());
                                            } else {
                                                System.out.println("任务" + num + "异常! e=" + throwable + ", " + new Date());
                                                errorMap.put(num.toString(), throwable.getMessage());
                                            }
                                            return "";
                                        }
                                    });
                        }
                );

        CompletableFuture[] completableFutures = completableFutureStream.toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(completableFutures)
                .whenComplete((v, th) -> {
                    System.out.println("所有任务执行完成触发\n resultList=" + resultList + "\n errorMap=" + errorMap + "\n耗时=" + (System.currentTimeMillis() - start));
                }).join();

        System.out.println("end");
//        executor.shutdown();
    }

    private static Integer getDouble(Integer i) {
        try {
            //任务1耗时3秒
            if (i == 1) Thread.sleep(1000);
            else //其它任务耗时1秒
                if (i == 2) {
                    //任务2耗时1秒,还出错
                    Thread.sleep(1000);
                    throw new RuntimeException("出异常了");
                } else Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 2 * i;
    }

}
