package com.example.lsy.userful.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class LambdaExceptionUtil {

    @FunctionalInterface
    public interface Function_WithExceptions<T, R, E extends Exception> {
        R apply(T t) throws E;
    }

    /**
     * .map(rethrowFunction(name -> Class.forName(name))) or .map(rethrowFunction(Class::forName))
     */
    public static <T, R, E extends Exception> Function<T, R> rethrowFunction(Function_WithExceptions<T, R, E> function) throws E  {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception exception) {
                doThrow(exception);
                return null;
            }
        };
    }

    @SuppressWarnings("unchecked")
    private static <E extends Exception> void doThrow(Exception exception) throws E {
        throw (E) exception;
    }

    public static class TestLambdaExceptionUtil {

        public static void main(String[] args) throws MyTestException {
            List<Integer> lists = new ArrayList<Integer>();
            lists.add(1);
            lists.add(2);
            lists.add(3);
            List<Integer> sizes = Stream.of("ciao", "hello").map(rethrowFunction(s -> transform(s))).collect(toList());
            sizes.stream().forEach(System.out::println);
        }

        private static Integer transform(String value) throws MyTestException {
            if(value != null) {
                throw new MyTestException();
            }
            return value.length();
        }

        private static class MyTestException extends Exception {}

    }
}

