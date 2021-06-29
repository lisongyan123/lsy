//package com.example.lsy.java11;
//
//import lombok.var;
//import org.assertj.core.condition.Not;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Supplier;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//public class stream {
//    public static void main(String[] args) {
//        List<Optional<String>> list = Arrays.asList (
//                Optional.empty(),
//                Optional.of("A"),
//                Optional.empty(),
//                Optional.of("B"));
//
//        //if optional is non-empty, get the value in stream, otherwise return empty
//        List<String> filteredList = list.stream()
//                .flatMap(o -> o.isPresent() ? Stream.of(o.get()) : Stream.empty())
//                .collect(Collectors.toList());
//
//        //Optional::stream method will return a stream of either one
//        //or zero element if data is present or not.
//        List<String> filteredListJava9 = list.stream()
//                .flatMap(Optional::stream)
//                .collect(Collectors.toList());
//
//        System.out.println(filteredList);
//        System.out.println(filteredListJava9);
//
//
//        Optional<Integer> optional = Optional.of(1);
//
//        optional.ifPresentOrElse( x -> System.out.println("Value: " + x),() ->
//                System.out.println("Not Present."));
////        Value: 1
//        optional = Optional.empty();
//
//        optional.ifPresentOrElse( x -> System.out.println("Value: " + x),() ->
//                System.out.println("Not Present."));
////        Not Present.
//
//
//        Optional<String> optional1 = Optional.of("Mahesh");
//        Supplier<Optional<String>> supplierString = () -> Optional.of("Not Present");
//        optional1 = optional1.or(supplierString);
//        optional1.ifPresent( x -> System.out.println("Value: " + x));
//        optional1 = Optional.empty();
//        optional1 = optional1.or(supplierString);
//        optional1.ifPresent( x -> System.out.println("Value: " + x));
////        Value: Mahesh
////        Value: Not Present
//        System.out.println("res:" + Stream.ofNullable(null).count());
////        res:0
//
//        System.out.println(Stream.of(1, 2, 3, 2, 1)
//                .takeWhile(n -> n < 3)
//                .collect(Collectors.toList()));
//
//        System.out.println(Stream.of(1, 2, 3, 2, 1)
//                .dropWhile(n -> n < 3)
//                .collect(Collectors.toList())); // [3, 2, 1]
//    }
//}
