package com.example.lsy.userful.stream;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Jdk9NewStreamApi {

    public static void main(String[] args) {
        takeWhile(Stream.of("a", "b", "", "c", "d"), String::isEmpty).forEach(System.out::println);//ab
        dropWhile(Stream.of("a", "b", "", "c", "d"), String::isEmpty).forEach(System.out::println);//cd
    }

    public static <T> Stream<T> dropWhile(Stream<T> stream, Predicate<? super T> predicate) {
        Spliterator<T> spliterator = stream.spliterator();
        return StreamSupport.stream(new Spliterator<T>() {
            boolean canGo = false;

            @Override
            public boolean tryAdvance(Consumer<? super T> action) {
                return spliterator.tryAdvance(item -> {
                    if (canGo) {
                        action.accept(item);
                    } else {
                        canGo = predicate.test(item);
                    }
                });

            }

            @Override
            public Spliterator<T> trySplit() {
                return null;
            }

            @Override
            public long estimateSize() {
                return 0;
            }

            @Override
            public int characteristics() {
                return 0;
            }
        }, false);
    }

    public static <T> Stream<T> takeWhile(Stream<T> stream, Predicate<? super T> predicate) {
        Spliterator<T> spliterator = stream.spliterator();
        return StreamSupport.stream(new Spliterator<T>() {
            boolean stillGoing = false;

            @Override
            public boolean tryAdvance(Consumer<? super T> action) {
                boolean hasNext = spliterator.tryAdvance(item -> {
                    if (!predicate.test(item)) {
                        action.accept(item);
                        stillGoing = true;
                    } else {
                        stillGoing = false;
                    }
                });
                return hasNext && stillGoing;
            }

            @Override
            public Spliterator<T> trySplit() {
                return null;
            }

            @Override
            public long estimateSize() {
                return 0;
            }

            @Override
            public int characteristics() {
                return 0;
            }
        }, false);
    }

//    static <T> Spliterator<T> takeWhile(
//            Spliterator<T> splitr, Predicate<? super T> predicate) {
//        return new Spliterators.AbstractSpliterator<T>(splitr.estimateSize(), 0) {
//            boolean stillGoing = true;
//
//            @Override
//            public boolean tryAdvance(Consumer<? super T> consumer) {
//                if (stillGoing) {
//                    boolean hadNext = splitr.tryAdvance(elem -> {
//                        if (predicate.test(elem)) {
//                            consumer.accept(elem);
//                        } else {
//                            stillGoing = false;
//                        }
//                    });
//                    return hadNext && stillGoing;
//                }
//                return false;
//            }
//        };
//    }
//
//    static <T> Stream<T> takeWhile(Stream<T> stream, Predicate<? super T> predicate) {
//        return java.util.stream.StreamSupport.stream(takeWhile(stream.spliterator(), predicate), false);
//    }
}
