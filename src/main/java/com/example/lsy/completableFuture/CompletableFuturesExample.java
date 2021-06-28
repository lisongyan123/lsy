package com.example.lsy.completableFuture;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * 1.创建异步操作，runAsync（不支持返回值） 和 supplyAsync方法（支持返回值）
 * 2.计算结果完成时的回调方法
 *    whenComplete：执行完当前任务的线程，继续执行 whenComplete 的任务。
 *    whenCompleteAsync： 执行完当前任务的线程，把whenCompleteAsync 的任务继续提交给线程池来执行。
 *    exceptionally：当前任务出现异常时，执行exceptionally中的回调方法。
 * 3.thenApply 方法，当一个线程依赖另一个线程时，可以使用 thenApply 方法来把这两个线程串行化。
 * 4.handle 方法
 * 5.handle 是执行任务完成时对结果的处理。
 * 6.handle 方法和 thenApply 方法处理方式基本一样。不同的是 handle 是在任务完成后再执行，还可以处理异常的任务。thenApply 只可以执行正常的任务，任务出现异常则不执行 thenApply 方法。
 * 7.thenAccept 消费处理结果，接收任务的处理结果，并消费处理，无返回结果。
 * 8.thenRun 方法，跟 thenAccept 方法不一样的是，不关心任务的处理结果。只要上面的任务执行完成，就开始执行 thenAccept 。
 * 9.thenCombine 合并任务，thenCombine 会把 两个 CompletionStage 的任务都执行完成后，把两个任务的结果一块交给 thenCombine 来处理。
 * 10.thenCompose 方法，thenCompose 方法允许你对两个 CompletionStage 进行流水线操作，第一个操作完成时，将其结果作为参数传递给第二个操作。
 * 11.whenComplete用的是主线程 thenApply和thenAccept用的是fork-join线程  所以当complete回调函数sleep 2s 时候 回调函数可能不会执行
 * 12.
 */
public class CompletableFuturesExample {

    public static void main(String[] args) throws Exception {
//        CompletableFuturesExample.thenApply();
//        CompletableFuturesExample.thenAccept();
//        CompletableFuturesExample.thenRun();
//        CompletableFuturesExample.thenCombine();
//        CompletableFuturesExample.runAfterBoth();
//        CompletableFuturesExample.applyToEither();
//        CompletableFuturesExample.anyOf();
//        CompletableFuturesExample.runAfterEither();
//        CompletableFuturesExample.doBoth2();
//        CompletableFuturesExample.handle1();
//        CompletableFuturesExample.handle2();
//        CompletableFuturesExample.thenAcceptBoth();
//        CompletableFuturesExample.whenComplete();
//        example();
//        threadSafe();
//        threadUnSafe();
//        allExample();
//        complete();
//        failAfter();
//        method();
        CompletableFuturesExample.orderBy();
    }

    private static void orderBy() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executorServe = new ThreadPoolExecutor(3,
                Runtime.getRuntime().availableProcessors() * 3,
                5,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(1000));

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "第1个耗时的任务执行完毕！【有参数】";
        }, executorServe);

        CompletableFuture<String> cf2 = cf1.thenApplyAsync(s -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return s + "；第2个耗时的任务执行完毕！【有参数】";
        }, executorServe);

        CompletableFuture<String> cf3 = cf2.thenApplyAsync(s -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return s + "；第3个耗时的任务执行完毕！【有参数】";
        }, executorServe);

        //链式调用先后顺序结束
        System.out.println(cf3.get());

        //不要写前面的返回值
        cf3.thenRun(() -> {
            System.out.println("第3个耗时的任务执行完毕！【无参数】");
        });
    }

    //    public static void s() {
//        ExecutorService pool = Executors.newFixedThreadPool(1000);
//        CompletableFuture[] futureList = list.parallelStream().map(item -> CompletableFuture.supplyAsync(() -> {
//                    for (int i = 0; i < 24; i++) {
//                        for (int j = 0; j < count; j++) {
//                            List<ElectricModel> device_info = new ArrayList<>();
//
//                            Timestamp upload_time = new Timestamp(item + i * 3600000 + (long) (Math.random() * 57 * 60 * 1000));
//                            Timestamp gather_time = upload_time;
//                            DeviceProtocol deviceProtocol = new DeviceProtocol();
//                            deviceProtocol.setTenant_id(tenant_id);
//                            deviceProtocol.setServer_id(server_id);
//                            deviceProtocol.setUpload_time(upload_time);
//                            deviceProtocol.setPrj_id(prj_id);
//                            for (String device_id : getDevices()) {
//                                ElectricModel model = new ElectricModel();
//                                model.setDevice_id(device_id);
//                                model.setGather_time(gather_time);
//                                model.setDevice_path(device_path);
//                                model.setState(0);
//                                model.setElectric_meter(createElectricMeter(device_id, gather_time.getTime()));
//
//                                if (getDuanluqiDevices().contains(device_id)) {
//                                    model.setEpt_stat(createDeviceStateParam(device_id));
//                                }
//
//                                device_info.add(model);
//                            }
//
//                            deviceProtocol.setDevice_info(device_info);
//
//                            //todo kafka send msg
//                            sendMsg(deviceProtocol);
//                        }
//                    }
//                    System.out.println(new Timestamp(item) + "任务完成");
//                    return item;
//                }, pool)
//                        .whenComplete((s, e) -> {
//
//                        })
//        ).toArray(CompletableFuture[]::new);
//        //所有任务执行完才放行
//        CompletableFuture.allOf(futureList).join();
//        //关闭线程池
//        pool.shutdown();
//    }

    private static void method() throws ExecutionException, InterruptedException {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "f1";
        });

        f1.whenCompleteAsync(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                System.out.println(System.currentTimeMillis() + ":" + s);
            }
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "f2";
        });

        f2.whenCompleteAsync(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
//              在这里sleep 2s 后发现回调函数不会被执行
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println(System.currentTimeMillis() + ":" + s);
            }
        });

        CompletableFuture<Void> all = CompletableFuture.allOf(f1, f2);
        //阻塞，直到所有任务结束。
        System.out.println(System.currentTimeMillis() + ":阻塞");
        all.join();
        System.out.println(System.currentTimeMillis() + ":阻塞结束");

        //一个需要耗时2秒，一个需要耗时3秒，只有当最长的耗时3秒的完成后，才会结束。
        System.out.println("任务均已完成。");
    }

    public static void getAllResult() {
        List<Long> list = new ArrayList<>(3);
        list.add(1000l);
        list.add(2000l);
        list.add(3000l);
        FutureTaskWorker<Long, String> futureTaskWorker = new FutureTaskWorker<>(list, (Long e) -> CompletableFuturesExample.getThreadName(e));
        long begin = System.currentTimeMillis();
        List<String> allResult = futureTaskWorker.getAllResult();
        long end = System.currentTimeMillis();
        System.out.println(allResult);
        System.out.println("结束耗时:" + (end - begin));
    }


    private static CompletableFuture<String> getThreadName(long sleepTime) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(sleepTime);
                System.out.println(Thread.currentThread().getName() + "已经睡眠" + sleepTime + "毫秒");
                return Thread.currentThread().getName();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public static void getThreadName() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                    //使用ForkJoinPool线程  F1  ForkJoinPool.commonPool-worker-1
                    System.out.println("F1  " + Thread.currentThread().getName());
                    return "F1";
                }
        );

        CompletableFuture<String> future2 = future.whenComplete((s, throwable) -> {
            System.out.println(s);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //使用主线程  F2  main
            System.out.println("F2  " + Thread.currentThread().getName());
        });
    }

    public static void completableOnTimeOut() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });

        CompletableFuture<Integer> within = CompletableFutureHandleTimeout.completeOnTimeout(1, future, 1, TimeUnit.SECONDS);
        System.out.println(within.get());

        CompletableFuture<String> futureStr = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "正常执行";
        });

        CompletableFuture<String> withinStr = CompletableFutureHandleTimeout.completeOnTimeout("异常执行", futureStr, 2, TimeUnit.SECONDS);
        System.out.println(withinStr.get());

    }

    public static void exceptionHandler() {
        //记录开始时间
        Long start = System.currentTimeMillis();

        //定长10线程池
        ExecutorService executor = Executors.newFixedThreadPool(3);

        //任务
        List<Integer> taskList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<String> resultList = new ArrayList<>();

        Map<String, String> errorList = new HashMap<>();

        Stream<CompletableFuture<String>> completableFutureStream = taskList.stream()
                .map(num -> {
                            return CompletableFuture
                                    .supplyAsync(() -> {
                                                return CompletableFuturesExample.getDouble(num);
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
                                                errorList.put(num.toString(), throwable.getMessage());
                                            }
                                            return "";
                                        }
                                    });
                        }
                );

        CompletableFuture[] completableFutures = completableFutureStream.toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(completableFutures)
                .whenComplete((v, th) -> {
                    System.out.println("所有任务执行完成触发\n resultList=" + resultList + "\n errorList=" + errorList + "\n耗时=" + (System.currentTimeMillis() - start));
                }).join();

        System.out.println("end");
    }

    private static Integer getDouble(Integer i) {
        try {
            //任务1耗时3秒
            if (i == 1) Thread.sleep(3000);
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

    /**
     * join和get都阻塞主线程 线程进入supplyAsync开始执行如果不用complete
     * complete、completeExceptionally、obtrudeValue（即使已经完成了，这个也会设置它）
     * 一下线程会返回b而不是a
     */
    public static void complete() {
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("started work");
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(5));
            System.out.println("done work");
            return "a";
        });

        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(4));
        cf.obtrudeValue("c");
        cf.complete("b");
        System.out.println(cf.join());
    }

    public static void failAfter() {
        CompletableFuture<Object> oneSecondTimeout = CompletableFutureHandleTimeout.failAfter(Duration.ofSeconds(2))
                .exceptionally(xxx -> "超时");
        List<Object> collect = Stream.of("1", "2", "3", "4", "5", "6", "7")
                .map(x -> CompletableFuture.anyOf(CompletableFuturesExample.createTaskSupplier(x)
                        , oneSecondTimeout))
                .collect(Collectors.toList())
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        System.out.println("-------结束------");
    }

    public static void allOf() throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture.allOf(
                CompletableFuture.supplyAsync(() -> CompletableFuturesExample.getData())
                        .whenComplete((v, err) -> System.out.println(Thread.currentThread() + ":::" + v))
                        .exceptionally(e -> "1错"),
                CompletableFuture.supplyAsync(() -> CompletableFuturesExample.getData())
                        .whenComplete((v, err) -> System.out.println(Thread.currentThread() + ":::" + v))
                        .exceptionally(e -> "1错"),
                CompletableFuture.supplyAsync(() -> CompletableFuturesExample.getData())
                        .whenComplete((v, err) -> System.out.println(Thread.currentThread() + ":::" + v))
                        .exceptionally(e -> "1错"))
//                .join();
                .get(3, TimeUnit.MINUTES);

        CompletableFuture[] completableFutures = new CompletableFuture[]{
                CompletableFuturesExample.createTaskSupplier("1"),
                CompletableFuturesExample.createTaskSupplier("2"),
                CompletableFuturesExample.createTaskSupplier("3"),
                CompletableFuturesExample.createTaskSupplier("4")
        };
        Object val = CompletableFuture.allOf(
                completableFutures
        ).join();
    }

    /**
     * 打印结果
     * 1624723801324:阻塞
     * 1624723804333:2执行完成
     * 1624723804333:1执行完成
     * 1624723804333:0执行完成
     * 1624723804334:阻塞结束
     * 1624723805335:1回调执行完成
     * 1624723805335:2回调执行完成
     * 1624723805335:0回调执行完成
     *
     * @throws InterruptedException
     */
    public static void example1() throws InterruptedException {
        List<CompletableFuture> comlist = new ArrayList<>();
        final int size = 3;
        CountDownLatch countDownLatch = new CountDownLatch(size);
        for (int i = 0; i < size; i++) {
            int res = i;
            CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(System.currentTimeMillis() + ":" + res + "执行完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return res + "";
            });
            f1.whenCompleteAsync((x, y) -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(System.currentTimeMillis() + ":" + x + "回调执行完成");
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            comlist.add(f1);
        }

        CompletableFuture<Void> all = CompletableFuture.allOf(comlist.toArray(new CompletableFuture[size]));
        //阻塞，直到所有任务结束。任务complete就会执行,handler里面不一定会执行..
        System.out.println(System.currentTimeMillis() + ":阻塞");
        all.join();
        System.out.println(System.currentTimeMillis() + ":阻塞结束");
        countDownLatch.await();
        System.out.println("回调都结束...");
    }

    /**
     * 打印结果
     * 1624723805339:阻塞
     * 1624723808344:2执行完成
     * 1624723808344:1执行完成
     * 1624723808344:0执行完成
     * 1624723809345:0回调执行完成
     * 1624723809345:2回调执行完成
     * 1624723809345:1回调执行完成
     * 1624723809364:阻塞结束
     *
     * @throws InterruptedException
     */
    public static void example2() throws InterruptedException {
        List<CompletableFuture> comlist = new ArrayList<>();
        final int size = 3;
        CountDownLatch countDownLatch = new CountDownLatch(size);
        for (int i = 0; i < size; i++) {
            int res = i;
            CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(System.currentTimeMillis() + ":" + res + "执行完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return res + "";
            });
            CompletableFuture<Object> f2 = f1.whenCompleteAsync((x, y) -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(System.currentTimeMillis() + ":" + x + "回调执行完成");
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).thenApply(ignore -> Stream.of(1, 2, 3)
                    .collect(Collectors.toList()));
            comlist.add(f2);
        }

        CompletableFuture<Void> all = CompletableFuture.allOf(comlist.toArray(new CompletableFuture[size]));
        //阻塞，直到所有任务结束。任务complete就会执行,handler里面不一定会执行..
        System.out.println(System.currentTimeMillis() + ":阻塞");
        all.join();
        System.out.println(System.currentTimeMillis() + ":阻塞结束");
        countDownLatch.await();
        System.out.println("回调都结束...");
    }

    private static CompletableFuture<String> createTaskSupplier(String x) {
        return CompletableFuture.supplyAsync(CompletableFuturesExample.getStringSupplier(x))
                .exceptionally(xx -> xx.getMessage());
    }


    private static Supplier<String> getStringSupplier(String text) {
        return () -> {
            System.out.println("开始 " + text);
            if ("1".equals(text)) throw new RuntimeException("运行时错误");
            try {
                if ("5".equals(text)) CompletableFuturesExample.setSleepTime(4);       //5号超时了
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("停止 " + text);
            return text + "号";
        };
    }


    private static void setSleepTime(int SleepTime) throws InterruptedException {
        TimeUnit.SECONDS.sleep(SleepTime);
    }


    private static String getData() {
        int anInt = new Random().nextInt(10);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(anInt);
        return anInt + "";
    }

    public static void allExample() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                System.out.println(Thread.currentThread().getName() + "\t completableFuture");
                //int i = 10 / 0;
                return 1024;
            }
        }).thenApply(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer o) {
                System.out.println("thenApply方法，上次返回结果：" + o);
                return o * 2;
            }
        }).whenComplete(new BiConsumer<Integer, Throwable>() {
            @Override
            public void accept(Integer o, Throwable throwable) {
                System.out.println("-------o=" + o);
                System.out.println("-------throwable=" + throwable);
            }
        }).exceptionally(new Function<Throwable, Integer>() {
            @Override
            public Integer apply(Throwable throwable) {
                System.out.println("throwable=" + throwable);
                return 6666;
            }
        }).handle(new BiFunction<Integer, Throwable, Integer>() {
            @Override
            public Integer apply(Integer integer, Throwable throwable) {
                System.out.println("handle o=" + integer);
                System.out.println("handle throwable=" + throwable);
                return 8888;
            }
        });
//        }).completeExceptionally(new TimeoutException());
        System.out.println(future.get());

        CompletableFuture.supplyAsync(() -> {
            return "hello";
        }).thenApplyAsync(t -> {
            return t + " world!";
        }).thenCombineAsync(CompletableFuture.completedFuture(" CompletableFuture"), (t, u) -> {
            return t + u;
        }).whenComplete((t, u) -> {
            System.out.println(t);
        });

        List<CompletableFuture> futures = Arrays.asList(
                CompletableFuture.completedFuture("hello"),
                CompletableFuture.completedFuture(" world!"),
                CompletableFuture.completedFuture(" hello"),
                CompletableFuture.completedFuture("java!")
        );
        CompletableFuture<Void> allCompleted = CompletableFuture.allOf(futures.toArray(new CompletableFuture[]{}));
        allCompleted.thenRun(() -> {
            futures.stream().forEach(future1 -> {
                try {
                    System.out.println("get future at:" + System.currentTimeMillis() + ", result:" + future1.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    public static void threadSafe() {
        System.out.println("----start----" + LocalDateTime.now());
        List<Integer> list = new ArrayList<>();
        List<CompletableFuture<Integer>> futureList = new ArrayList<>();
        IntStream.range(0, 10000).forEach(i -> {
            //设置随机返回数字
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                Random random = new Random();
                return random.nextInt(Integer.MAX_VALUE);
            });
            futureList.add(future);
        });
        //主线程阻塞等待所有异步线程完成任务，并在join返回结果再添加到ArrayList，就不会出现线程安全问题
        futureList.forEach(future -> list.add(future.join()));
        System.out.println("testCompletableFuture3 - size : " + list.size());
        System.out.println("----end----" + LocalDateTime.now());
    }

    public static void threadUnSafe() {
        System.out.println("----start----" + LocalDateTime.now());
        //由于方法后面的add操作，这里的变量相当于全局变量，造成了线程安全问题出现
        List<Integer> list = new ArrayList<>();
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        IntStream.range(0, 10000).forEach(i -> {
            //设置随机返回数字
            CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
                Random random = new Random();
                return random.nextInt(Integer.MAX_VALUE);
            }).thenAccept(list::add);       //添加到list，出现线程安全问题
            futureList.add(future);
        });
        //主线程阻塞等待所有异步线程完成任务
        futureList.forEach(CompletableFuture::join);
        System.out.println("testCompletableFuture - size :" + list.size());
        System.out.println("----end----" + LocalDateTime.now());
        System.out.println();
    }

    private static void example() {
        CompletableFuture.supplyAsync(() -> {
            int ms = new Random().nextInt(100);
            LockSupport.parkNanos(MILLISECONDS.toNanos(ms));
            String msg = format("supplyAsync %s ms", ms);
            System.out.println(format(msg));
            return msg;
        }).thenApply(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                System.out.println(format("thenApply apply s.length()"));
                return s.length();
            }
        }).whenComplete(new BiConsumer<Integer, Throwable>() {
            @Override
            public void accept(Integer s, Throwable throwable) {
                System.out.println(format("done " + s));
            }
        });
    }

    private static void doBoth2() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        //任务1
        CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("任务1");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("任务2");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "返回数据async";
            }
        }, executor);
        //任务2
        CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println("任务3");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("任务4");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "返回数据async2";
            }
        }, executor);
        System.out.println("任务执行完成");
        executor.shutdown();
    }


    /**
     * 在两个任务运行完执行
     */
    private static void runAfterBoth() {
        CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("第一个任务执行完成");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).runAfterBothAsync(CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("第二个任务执行完成");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s2";
        }), () -> System.out.println("hello world"));
        while (true) {
        }
    }

    /**
     * hello world
     */
    private static void thenApply() {
        String result = CompletableFuture.supplyAsync(() -> "hello").thenApply(s -> s + " world").join();
        System.out.println(result);
    }

    /**
     * hello world
     */
    private static void thenAccept() {
        CompletableFuture.supplyAsync(() -> "hello").thenAccept(s -> System.out.println(s + " world"));
    }

    /**
     * hello world
     */
    private static void thenRun() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenRun(() -> System.out.println("hello world"));
        while (true) {
        }
    }

    private static void thenCombine() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        }), (s1, s2) -> s1 + " " + s2).join();
        System.out.println(result);
    }

    private static void thenAcceptBoth() {
        LocalDateTime start = LocalDateTime.now();
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        }), (s1, s2) -> System.out.println(s1 + " " + s2));
        while (true) {
        }
    }

    /**
     * 谁计算的快就用谁
     */
    private static void applyToEither() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello world";
        }), s -> s).join();
        System.out.println(result);
    }

    /**
     * 谁计算的快就用谁
     */
    public static void acceptEither() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello world";
        }), System.out::println);
        while (true) {
        }
    }

    /**
     * 两个任务 任何一个完成了在执行下一步操作
     */
    private static void runAfterEither() {
        CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("第一个任务");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("第二个任务");
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s2";
        }), () -> System.out.println("hello world"));
        while (true) {
        }
    }

    /**
     * 任务出错可以解决
     */
    public static void exceptionally() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1 == 1) throw new RuntimeException("测试一下异常情况");
            return "s1";
        }).exceptionally(e -> {
            System.out.println(e.getMessage());
            return "hello world";
        }).join();
        System.out.println(result);
    }

    /**
     * 运行完成对结果的记录
     */
    public static void whenComplete() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1 == 1) throw new RuntimeException("测试一下异常情况");
            return "s1";
        }).whenComplete((s, t) -> {
            System.out.println(s);
            System.out.println(t.getMessage());
        }).exceptionally(e -> {
            System.out.println(e.getMessage());
            return "hello world";
        }).join();
        System.out.println(result);
    }

    /**运行结束后对结果的处理，一种是正常执行，返回值。另外一种是遇到异常抛出造成程序的中断。*/
    /**
     * 出现异常
     */
    private static void handle1() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //出现异常
            if (0 == 1) throw new RuntimeException("测试一下异常情况");
            return "s1";
        }).handle((s, t) -> {
            if (t != null) return "hello world";
            return s;
        }).join();
        System.out.println(result);
    }

    /**
     * 未出现异常
     */
    public static void handle2() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).handle((s, t) -> {
            if (t != null) return "hello world";
            return s;
        }).join();
        System.out.println(result);
    }

    private static void anyOf() throws InterruptedException {
        // 两个CompletableFuture执行异步查询:
        CompletableFuture<String> cfQueryFromSina = CompletableFuture.supplyAsync(() -> {
            return CompletableFuturesExample.queryCode("中国石油", "https://finance.sina.com.cn/code/");
        });
        CompletableFuture<String> cfQueryFrom163 = CompletableFuture.supplyAsync(() -> {
            return CompletableFuturesExample.queryCode("中国石油", "https://money.163.com/code/");
        });
        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Object> cfQuery = CompletableFuture.anyOf(cfQueryFromSina, cfQueryFrom163);
        // 两个CompletableFuture执行异步查询:
        CompletableFuture<Double> cfFetchFromSina = cfQuery.thenApplyAsync((code) -> {
            return CompletableFuturesExample.fetchPrice((String) code, "https://finance.sina.com.cn/price/");
        });
        CompletableFuture<Double> cfFetchFrom163 = cfQuery.thenApplyAsync((code) -> {
            return CompletableFuturesExample.fetchPrice((String) code, "https://money.163.com/price/");
        });
        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Object> cfFetch = CompletableFuture.anyOf(cfFetchFromSina, cfFetchFrom163);

        // 最终结果:
        cfFetch.thenAccept((result) -> {
            System.out.println("price: " + result);
        });

        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(200);
    }

    private static String queryCode(String name, String url) {
        System.out.println("query code from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
        }
        return "601857";
    }

    private static Double fetchPrice(String code, String url) {
        System.out.println("query price from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
        }
        return 5 + Math.random() * 20;
    }
}
