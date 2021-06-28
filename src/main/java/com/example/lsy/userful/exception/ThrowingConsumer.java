package com.example.lsy.userful.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@FunctionalInterface
public interface ThrowingConsumer<T> extends Consumer<T> {

    @Override
    default void accept(final T elem) {
        try {
            acceptThrows(elem);
        } catch (final Exception e) {
            // Implement your own exception handling logic here..
            // For example:
            System.out.println("handling an exception...");
            // Or ...
            throw new RuntimeException(e);
        }
    }

    void acceptThrows(T elem) throws Exception;

    static void main(String[] args) {
        List<String> lists = new ArrayList<String>();
        lists.add("1");
        lists.add("2");
        lists.add("3");
        lists.forEach((ThrowingConsumer<String>) aps -> {
            // maybe some other code here...
            throw new Exception("asda");
        });
    }
}