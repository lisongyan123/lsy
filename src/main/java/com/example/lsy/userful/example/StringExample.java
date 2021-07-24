package com.example.lsy.userful.example;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringExample {

    public static void main(String[] args) {
//        testJoin();
//        testChars();
//        testPatternPredicate();
//        testPatternSplit();
        Map map = (Map) getMap().put("1",2);
        map.putIfAbsent("1",2);
        System.out.println("getMap():" + map);
    }

    private static Map getMap() {
        Map map = new HashMap<String,Object>(8) {
            @Override
            public Map put(String key, Object value) {
                Map map = new HashMap() {
                    @Override
                    public Map put(Object key, Object value) {
                        return put("1", "2");
                    }

                    @Override
                    public Object putIfAbsent(Object key, Object value) {
                        return super.putIfAbsent("3", "4");
                    }
                };
                return map;
            }
        };
        return map;
    }

    private static void testChars() {
        String string = "foobar:foo:bar"
                .chars()
                .distinct()
                .mapToObj(c -> String.valueOf((char) c))
                .sorted()
                .collect(Collectors.joining());
        System.out.println(string);
    }

    private static void testPatternSplit() {
        String string = Pattern.compile(":")
                .splitAsStream("foobar:foo:bar")
                .filter(s -> s.contains("bar"))
                .sorted()
                .collect(Collectors.joining(":"));
        System.out.println(string);
    }

    private static void testPatternPredicate() {
        long count = Stream.of("bob@gmail.com", "alice@hotmail.com")
                .filter(Pattern.compile(".*@gmail\\.com").asPredicate())
                .count();
        System.out.println(count);
    }

    private static void testJoin() {
        String string = String.join(":", "foobar", "foo", "bar");
        System.out.println(string);
    }
}
