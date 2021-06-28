package com.example.lsy.asserts;

import org.springframework.util.StopWatch;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class SpringStopWatch {
    public static void main(String[] args) throws Exception {
        // 强烈每一个秒表都给一个id，这样查看日志起来能够更加的精确
        // 至于Id 我觉得给UUID是可行的~
        StopWatch sw = new StopWatch(UUID.randomUUID().toString());

        sw.start("起床");
        Thread.sleep(1000);
        System.out.println("当前任务名称：" +  sw.currentTaskName());
        sw.stop();

        sw.start("洗漱");
        Thread.sleep(2000);
        System.out.println("当前任务名称：" +  sw.currentTaskName());
        sw.stop();

        sw.start("锁门");
        Thread.sleep(500);
        System.out.println("当前任务名称：" + sw.currentTaskName());
        sw.stop();


        System.out.println(sw.prettyPrint()); // 这个方法打印在我们记录日志时是非常友好的  还有百分比的分析哦
//        System.out.println(sw.shortSummary());
//        System.out.println(sw.currentTaskName()); // stop后它的值为null


        // 最后一个任务的相关信息
//        System.out.println(sw.getLastTaskName());
//        System.out.println(sw.getLastTaskInfo());

        // 任务总的耗时  如果你想获取到每个任务详情（包括它的任务名、耗时等等）可使用
        System.out.println("所有任务总耗时："+  sw.getTotalTimeMillis());
        System.out.println("任务总数："+   sw.getTaskCount());
//        System.out.println("所有任务详情："+   sw.getTaskInfo()); // 拿到所有的任务





        CompletableFuture<String> futureA = CompletableFuture.
                supplyAsync(() -> "执行结果:" + (100 / 0))
                .thenApply(s -> "apply result:" + s)
                .handle((s, e) -> {
                    if (e == null) {
                        System.out.println(s);//未执行
                    } else {
                        System.out.println(e.getMessage());//java.lang.ArithmeticException: / by zero
                    }
                    return "handle result:" + (s == null ? "500" : s);
                })
                .exceptionally(e -> {
                    System.out.println("ex:" + e.getMessage()); //未执行
                    return "futureA result: 100";
                });
        System.out.println(futureA.join());//handle result:500
    }
}
