package com.example.lsy.path;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Test {
    public static void main(String[] args) {
        String[] strs = new String[]{"11,12"};

        List<String> stringList = Arrays.asList(strs);
        String a = stringList.stream().collect(Collectors.joining(","));
        System.out.println("a:" + a);

        String b = stringList.stream().map(v -> v + "ss").collect(Collectors.joining(";"));
        System.out.println("b:" + b);

//        String c = stringList.stream().map(v -> v + "ss").flatMap(Arrays::stream).collect(Collectors.joining(";"));
//        System.out.println("c:" + c);

        List sss = stringList.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .map(v -> v + " ")
                .collect(toList());
        sss.forEach(System.out::print);
    }
}
