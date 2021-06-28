package com.example.lsy.userful.pattern;

public class StrategyPattern  {
    public static void main(String[] args) {
//        ValidationStrategy validationStrategy = (String s) -> s.matches("\\d+");
        Validator v3 = new Validator((String s) -> s.matches("\\d+"));
        System.out.println(v3.validate("aaaa"));
        Validator v4 = new Validator((String s) -> s.matches("[a-z]+"));
        System.out.println(v4.validate("bbbb"));
    }

    public static class Validator {
        private final ValidationStrategy validationStrategy;

        public Validator(ValidationStrategy validationStrategy) {
            this.validationStrategy = validationStrategy;
        }

        public boolean validate(String s) {
            return validationStrategy.execute(s);
        }
    }

    interface ValidationStrategy {
        boolean execute(String s);
    }
}
