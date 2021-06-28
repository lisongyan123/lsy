package com.example.lsy.userful.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


/**
 * 将其包装成一个 RuntimeException 并且创建一个简单的包装工具来复用它，这样你就不需要每次都写try/catch 了
 * 如果你想要有更多的控制权，那你可以使用 Either 或者 Try 类型来包装方法执行的结果，这样你就可以将结果当成一段数据来处理了，并且当抛出 RuntimeException 时，你的流也不会终止。
 * 如果你不想自己封装 Either 或者 Try 类型，那么你可以选择已有的工具库来使用
 */
@Slf4j
public class ErrorHandler {

    public static void main(String[] args) {
        List<User> userList = new ArrayList<>();

        User user1 = new User(1L, "张三", 24);
        User user2 = new User(2L, "李四", 27);
        User user3 = new User(3L, "王五", 21);

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        /** 报异常就中断流 */
        userList.stream()
                .map(Either.warp(v->v.getAge()/1))     //这里不会抛出异常 二十返回值
                .forEach(System.out::println);

        /**简单粗暴  直接日志处理*/
        userList.stream()
                .map(v->{
                    try{
                        return v.getAge()/1;
                    }catch (Exception e){
                        doSomeThing3();
                    }
                    return 0;
                })     //这里不会抛出异常 二十返回值
                .forEach(System.out::println);

        /**过滤左侧实例  打印日志*/
        userList.stream()
                .map(Either.lift(item -> doSomeThing1()))     //这里不会抛出异常 二十返回值
                .forEach(System.out::println);

        userList.stream()
                .map(Either.liftWithValue(item -> doSomeThing2(item)))     //这里不会抛出异常 二十返回值
                .forEach(System.out::println);

//        Try<Integer> result = Try.of(() -> 1 / 0);
//        int errorSentinel = result.getOrElse(-1);
    }

    private static Object doSomeThing1() throws Exception {
        throw new Exception("完啦");
    }

    private static <T> T doSomeThing2(T t){
        return t;
    }

    private static void  doSomeThing3() {
        log.error("接口异常了啦");
    }

    @Data
    public static class User {
        Long id;
        String name;
        Integer age;

        public User(Long id, String name, Integer age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }
    }
}


