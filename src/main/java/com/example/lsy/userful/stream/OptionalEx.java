package com.example.lsy.userful.stream;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class OptionalEx<T> {
    private boolean isPresent;

    private OptionalEx(boolean isPresent) {
        this.isPresent = isPresent;
    }

    public void orElse(Runnable runner) {
        if (!isPresent) {
            runner.run();
        }
    }

    public static <T> OptionalEx ifPresent(Optional<T> opt, Consumer<? super T> consumer) {
        if (opt.isPresent()) {
            consumer.accept(opt.get());
            return new OptionalEx(true);
        }
        return new OptionalEx(false);
    }

    public static <T> Optional<T> or(Supplier<? extends Optional<? extends T>> supplier, Optional<T> optional, T value) {
        Objects.requireNonNull(supplier);
        if (value != null) {
            return optional;
        } else {
            @SuppressWarnings("unchecked")
            Optional<T> r = (Optional<T>) supplier.get();
            return Objects.requireNonNull(r);
        }
    }

    public static <T> void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction, T value) {
        if (value != null) {
            action.accept(value);
        } else {
            emptyAction.run();
        }
    }

    public static void run() {
        Opt<String> opt = Opt.of("I'm a cool text");
        opt.ifPresent()
                .apply(s -> System.out.printf("Text is: \n" + s))
                .elseApply(() -> System.out.println("no text available"));

        opt = Opt.of("This is the text");
        opt.ifNotPresent().apply(() -> System.out.println("Not present")).elseApply(t -> System.out.println("present"));
        Consumer<Optional<Integer>> c = OptionalConsumer.of(System.out::println, () -> {
            System.out.println("Not fit");
        });
        IntStream.range(0, 100).boxed().map(i -> Optional.of(i).filter(j -> j % 2 == 0)).forEach(c);
        Integer a = null;
//        ifPresent(opt1, x -> System.out.println("found " + x)) .orElse(() -> System.out.println("NOT FOUND"));
    }

    public static void or() {
        Optional<String> optional1 = Optional.of("Mahesh");
        Supplier<Optional<String>> supplierString = () -> Optional.of("Not Present");
        or(supplierString, optional1, "Mahesh").ifPresent(x -> System.out.println("Value: " + x));

        or(supplierString, Optional.empty(), null).ifPresent(x -> System.out.println("Value: " + x));
    }

    public static void ifPresentOrElse() {
        Optional<Integer> optional = Optional.of(1);
        ifPresentOrElse(
                x -> System.out.println("Value: " + x),
                () -> System.out.println("Not Present."),
                1
        );
//        Value: 1
        optional = Optional.empty();

        ifPresentOrElse(
                x -> System.out.println("Value: " + x),
                () -> System.out.println("Not Present."),
                null
        );
//        Not Present.
    }

    public static void main(String[] args) {
//        run();
        or();

    }
}