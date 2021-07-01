package com.example.lsy.userful.stream;

import java.util.Optional;
import java.util.function.Consumer;

public class OptionalConsumerExample<T> {
    private Optional<T> optional;

    private OptionalConsumerExample(Optional<T> optional) {
        this.optional = optional;
    }

    public static <T> OptionalConsumerExample<T> of(Optional<T> optional) {
        return new OptionalConsumerExample<>(optional);
    }

    public OptionalConsumerExample<T> ifPresent(Consumer<T> c) {
        optional.ifPresent(c);
        return this;
    }

    public OptionalConsumerExample<T> ifNotPresent(Runnable r) {
        if (!optional.isPresent()) r.run();
        return this;
    }
}