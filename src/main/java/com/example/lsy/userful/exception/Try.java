package com.example.lsy.userful.exception;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class Try<A> {

    interface ThrowableFunction<A, B> {
        B apply(A a) throws Exception;
    }

    public static boolean isSuccess(Try tryy) {
        return tryy instanceof Success;
    }

    public static <A, B> Function<A, Try<B>> tryOf(ThrowableFunction<A, B> function) {
        return a -> {
            try {
                B result = function.apply(a);
                return new Success<B>(result);
            } catch (Exception e) {
                return new Failure<>(e);
            }
        };
    }

    public abstract boolean isSuccess();

    public boolean isError() {
        return !isSuccess();
    }

    public abstract A getResult();

    public abstract Exception getError();

    static class Success<A> extends Try<A> {

        private final A result;

        public Success(A result) {
            this.result = result;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public A getResult() {
            return result;
        }

        @Override
        public Exception getError() {
            return new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object that) {
            if(!(that instanceof Success)) {
                return false;
            }
            return Objects.equals(result, ((Success) that).getResult());
        }
    }

    static class Failure<A> extends Try<A> {

        private final Exception exception;

        public Failure(Exception exception) {
            this.exception = exception;
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public A getResult() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Exception getError() {
            return exception;
        }
    }

    static class Test {
        public static void main(String[] args) {
            List<Integer> lists = new ArrayList<Integer>();
            lists.add(1);
            lists.add(2);
            lists.add(3);
            List<Try<Integer>> result = lists.stream().
                    map(Try.<Integer, Integer>tryOf(i -> 1/0)).
                    collect(Collectors.toList());
            result.stream().forEach(System.out::println);
        }
    }
}




