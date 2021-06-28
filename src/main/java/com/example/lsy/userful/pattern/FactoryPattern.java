package com.example.lsy.userful.pattern;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FactoryPattern {
    public static void main(String[] args) {
        /**
         * 引用贷款构造函数(lambda表达式)
         */
        Supplier<Product> loanSupplier = Loan::new;
        Product p2 = loanSupplier.get();
        Product p3 = ProductFactory.createProductLambda("stock");
        p2.doIt();
        p3.doIt();
    }

    final static private Map<String, Supplier<Product>> map = new HashMap<>();
    static {
        map.put("loan", Loan::new);
        map.put("stock", Stock::new);
        map.put("bond", Bond::new);
    }

    static private class ProductFactory {
        public static Product createProductLambda(String name){
            Supplier<Product> p = map.get(name);
            if(p != null) return p.get();
            throw new RuntimeException("No such product " + name);
        }
    }

    static private interface Product { public void doIt();}
    static private class Loan implements Product {
        @Override
        public void doIt() {
            System.out.println("loan");
        }
    }
    static private class Stock implements Product {
        @Override
        public void doIt() {
            System.out.println("Stock");
        }
    }
    static private class Bond implements Product {
        @Override
        public void doIt() {
            System.out.println("Bond");
        }
    }

    public interface TriFunction<T, U, V, R>{
        R apply(T t, U u, V v);
    }
    Map<String, TriFunction<Integer, Integer, String, Product>> newMap = new HashMap<>();
}
