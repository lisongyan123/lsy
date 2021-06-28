package com.example.lsy.userful.pattern;

import java.util.function.Consumer;

public class ModuleFuncPattern {
    public static void main(String[] args) {
        new AbstractOnlineBanking().processCustomer(1337, (AbstractOnlineBanking.Customer c) -> System.out.println("Hello!"));
    }

    public static class AbstractOnlineBanking {
        public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
            Customer customer = Database.getCustomerWithId(id);
            makeCustomerHappy.accept(customer);
        }

        private static class Customer {}

        private static class Database {
            static Customer getCustomerWithId(int id) {
                return new Customer();
            }
        }
    }

}
