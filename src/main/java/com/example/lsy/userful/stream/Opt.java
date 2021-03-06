package com.example.lsy.userful.stream;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class Opt<T> {
    private Optional<T> optional;

    private Opt(final Optional<T> theOptional) {
        this.optional = theOptional;
    }

    public static <T> Opt<T> of(final T value) {
        return new Opt<>(Optional.of(value));
    }

    public static <T> Opt<T> of(final Optional<T> optional) {
        return new Opt<>(optional);
    }

    public static <T> Opt<T> ofNullable(final T value) {
        return new Opt<>(Optional.ofNullable(value));
    }

    public static <T> Opt<T> empty() {
        return new Opt<>(Optional.empty());
    }

    private final BiFunction<Consumer<T>, Runnable, Void> ifPresent = (present, notPresent) -> {
        if (optional.isPresent()) {
            present.accept(this.optional.get());
        } else {
            notPresent.run();
        }
        return null;
    };
    private final BiFunction<Runnable, Consumer<T>, Void> ifNotPresent = (notPresent, present) -> {
        if (!optional.isPresent()) {
            notPresent.run();
        } else {
            present.accept(this.optional.get());
        }
        return null;
    };

    public EFunction<Consumer<T>, EFunction<Runnable, Void>> ifPresent() {
        return Opt.curry(this.ifPresent);
    }

    public EFunction<Runnable, EFunction<Consumer<T>, Void>> ifNotPresent() {
        return Opt.curry(this.ifNotPresent);
    }

    private static <X, Y, Z> EFunction<X, EFunction<Y, Z>> curry(final BiFunction<X, Y, Z> function) {
        return (final X x) -> (final Y y) -> function.apply(x, y);
    }
}