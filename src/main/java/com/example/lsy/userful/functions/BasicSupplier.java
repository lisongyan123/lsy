package com.example.lsy.userful.functions;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class BasicSupplier<T> implements Supplier<T> {

    private Class<T> type;

    public BasicSupplier(Class<T> type) {
        this.type = type;
    }

    @Override
    public T get() {
        try {
            // Assumes type is a public class:
            return type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // Produce a default Supplier from a type token:
    public static <T> Supplier<T> create(Class<T> type) {
        return new BasicSupplier<>(type);
    }

    static class CountedObject {
        private static long counter = 0;
        private final long id = counter++;

        public long id() {
            return id;
        }

        @Override
        public String toString() {
            return "CountedObject " + id;
        }
    }

    public static class BasicSupplierDemo {
        public static void main(String[] args) {
            Stream.generate(
                    BasicSupplier.create(CountedObject.class))
                    .limit(10)
                    .forEach(System.out::println);
        }
    }
}
