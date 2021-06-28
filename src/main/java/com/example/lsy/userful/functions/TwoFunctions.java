package com.example.lsy.userful.functions;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Function只接收一个参数 BiFunction俩
 */
public class TwoFunctions {
    public static void main(String[] args) {
        System.out.println("============Function============");
        Function<Integer,Integer> A= i->i+1;
        Function<Integer,Integer> B=i->i*i;
        System.out.println("F1:"+B.apply(A.apply(5)));
        System.out.println("F1:"+B.compose(A).apply(5));
        System.out.println("F2:"+A.apply(B.apply(5)));
        System.out.println("F2:"+B.andThen(A).apply(5));

        System.out.println("============BiFunction============");
        TwoFunctions test = new TwoFunctions();
        System.out.println(test.compute(2, 3, (v1, v2) -> v1 + v2, v2 -> v2 * v2));
    }

    public int compute(int a, int b, BiFunction<Integer, Integer, Integer> biFunction, Function<Integer, Integer> function) {
        return biFunction.andThen(function).apply(a, b);
    }
}
