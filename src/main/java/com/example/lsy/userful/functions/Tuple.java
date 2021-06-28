package com.example.lsy.userful.functions;

/**
 * 存储两个对象的元组
 * @param <A>
 * @param <B>
 */
public class Tuple<A,B> {

    public static class TupleTest {

        static Tuple<String, Integer> f() {
            return new Tuple<>("hi", 47);
        }

        static Tuple2<Amphibian, String, Integer> g() {
            return new Tuple2<>(new Amphibian(), "hi", 47);
        }

        static Tuple3<Vehicle, Amphibian, String, Integer> h() {
            return new Tuple3<>(new Vehicle(), new Amphibian(), "hi", 47);
        }

        static Tuple4<Vehicle, Amphibian, String, Integer, Double> k() {
            return new Tuple4<>(new Vehicle(), new Amphibian(), "hi", 47, 11.1);
        }

        /**
         * Tuple{a=hi, b=47}
         * Tuple{a=com.lsy.dubbo.utils.javaBase.泛型.Tuple$Amphibian@85ede7b, b=hi}
         * Tuple{a=com.lsy.dubbo.utils.javaBase.泛型.Tuple$Vehicle@5674cd4d, b=com.lsy.dubbo.utils.javaBase.泛型.Tuple$Amphibian@63961c42}
         * Tuple{a=com.lsy.dubbo.utils.javaBase.泛型.Tuple$Vehicle@65b54208, b=com.lsy.dubbo.utils.javaBase.泛型.Tuple$Amphibian@1be6f5c3}
         * @param args
         */
        public static void main(String[] args) {
            System.out.println(f());
            // ttsi.a1 = "there"; // 编译错误，因为 final 不能重新赋值
            System.out.println(g());
            System.out.println(h());
            System.out.println(k());
        }
    }

    public static class TupleTest2 {
        static Tuple<String, Integer> f() {
            return tuple("hi", 47);
        }

        static Tuple f2() {
            return tuple("hi", 48);
        }

        static Tuple2<Amphibian, String, Integer> g() {
            return tuple(new Amphibian(), "hi", 47);
        }

        static Tuple3<Vehicle, Amphibian, String, Integer> h() {
            return tuple(
                    new Vehicle(), new Amphibian(), "hi", 47);
        }

        static Tuple4<Vehicle, Amphibian, String, Integer, Double> k() {
            return tuple(new Vehicle(), new Amphibian(),
                    "hi", 47, 11.1);
        }

        public static void main(String[] args) {
            Tuple<String, Integer> ttsi = f();
            System.out.println(ttsi);
            System.out.println(f2());
            System.out.println(g());
            System.out.println(h());
            System.out.println(k());
        }
    }

    public final A a;
    public final B b;

    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public String rep() {
        return a + "," + b;
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }

    static class Tuple2<A,B,C> extends Tuple<A,B> {

        public final C c;




        public Tuple2(A a, B b, C c) {
            super(a, b);
            this.c = c;
        }

        public String rep() {
            return super.rep() + "," + c;
        }
    }

    static class Tuple3<A, B, C, D> extends Tuple2<A, B, C> {
        public final D d;
        public Tuple3(A a, B b, C c, D d) {
            super(a, b, c);
            this.d = d;
        }

        @Override
        public String rep() {
            return super.rep() + ", " + d;
        }
    }

    static class Tuple4<A, B, C, D, E> extends Tuple3<A, B, C, D> {
        public final E e;
        public Tuple4(A a, B b, C c, D d, E e) {
            super(a, b, c, d);
            this.e = e;
        }

        @Override
        public String rep() {
            return super.rep() + ", " + e;
        }
    }

    static class Amphibian {}

    // generics/Vehicle.java
    static class Vehicle {}

    /**简化元组的使用*/
    public static <A, B> Tuple<A, B> tuple(A a, B b) {
        return new Tuple<>(a, b);
    }

    public static <A, B, C> Tuple2<A, B, C>
    tuple(A a, B b, C c) {
        return new Tuple2<>(a, b, c);
    }

    public static <A, B, C, D> Tuple3<A, B, C, D>
    tuple(A a, B b, C c, D d) {
        return new Tuple3<>(a, b, c, d);
    }

    public static <A, B, C, D, E>
    Tuple4<A, B, C, D, E> tuple(A a, B b, C c, D d, E e) {
        return new Tuple4<>(a, b, c, d, e);
    }
}





