package com.example.lsy.completableFuture;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Future自动化轮子
 *
 * @ClassName FutureTaskWorker
 * @Description 异步执行同步返回
 */

public class FutureTaskWorker<T, R> {
    /**
     * @description 需要异步执行的任务
     */
    private List<T> taskList;

    public FutureTaskWorker(List<T> taskList, Function<T, CompletableFuture<R>> workFunction) {
        this.taskList = taskList;
        this.workFunction = workFunction;
    }

    /**
     * @description 需要执行的方法
     */
    private Function<T, CompletableFuture<R>> workFunction;

    public List<T> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<T> taskList) {
        this.taskList = taskList;
    }

    public Function<T, CompletableFuture<R>> getWorkFunction() {
        return workFunction;
    }

    public void setWorkFunction(Function<T, CompletableFuture<R>> workFunction) {
        this.workFunction = workFunction;
    }

    /**
     * @description 搜集执行结果
     * @author gang.tu
     * @return: java.util.List<R>
     */
    public List<R> getAllResult() {
        List<CompletableFuture<R>> futureList = taskList.stream().map(workFunction).collect(Collectors.toList());
        CompletableFuture<Void> allCompletableFuture = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()]));
        return allCompletableFuture.thenApply(e -> futureList.stream().map(CompletableFuture::join).collect(Collectors.toList())).join();
    }


}
