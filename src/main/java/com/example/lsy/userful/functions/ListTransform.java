package com.example.lsy.userful.functions;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


public class ListTransform {
    public static void main(String[] args) {
        Predicate<String> nameStartWithS = name -> name.startsWith("s");

        Consumer<String> sendEmail = message -> System.out.println("Sending email >> " + message);

        Function<String, Integer> stringToLength = name -> name.length();

        Supplier<String> uuidSupplier = () -> UUID.randomUUID().toString();

        Boolean bool = nameStartWithS.test("habi");
        System.out.println(bool);

        List<String> names = Arrays.asList("shekhar", "rahul", "sameer");

        List<Integer> namesLength = transform(names, String::length);
        System.out.println(namesLength);

        List<String> upperCaseNames = transform(names, String::toUpperCase);
        System.out.println(upperCaseNames);

        List<Double> numbers = transform(Arrays.asList(1.0, 2.0, 3.9), Double::doubleValue);
        System.out.println(numbers);
    }

    private static <T, R> List<R> transform(List<T> list, Function<T, R> fx) {
        List<R> result = new ArrayList<>();
//        list.stream().forEach(v->result.add(fx.apply(v)));
        for (T element : list) {
            result.add(fx.apply(element));
        }
        return result;
    }
}
