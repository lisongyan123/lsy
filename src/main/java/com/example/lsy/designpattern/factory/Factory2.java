package com.example.lsy.designpattern.factory;

public class Factory2 {
    public static void main(String[] args) {
        AbstractFactory productFactory = FactoryProducer.getFactory("Producer");

        Product product1 = productFactory.getProduct("Product1");
        Product product2 = productFactory.getProduct("Product2");
        Product product3 = productFactory.getProduct("Product3");
        product1.product();
        product2.product();
        product3.product();

        AbstractFactory consumerFactory = FactoryProducer.getFactory("Consumer");
        Consumer consumer1 = consumerFactory.getConsumer("Consumer1");
        Consumer consumer2 = consumerFactory.getConsumer("Consumer2");
        Consumer consumer3 = consumerFactory.getConsumer("Consumer3");
        consumer1.consumer();
        consumer2.consumer();
        consumer3.consumer();
    }
}

interface Product {
    void product();
}

interface Consumer {
    void consumer();
}

class Product1 implements Product {
    @Override
    public void product() {
        System.out.println("Product1");
    }
}

class Product2 implements Product {
    @Override
    public void product() {
        System.out.println("Product2");
    }
}

class Product3 implements Product {
    @Override
    public void product() {
        System.out.println("Product3");
    }
}

class Consumer1 implements Consumer {
    @Override
    public void consumer() {
        System.out.println("Consumer1");
    }
}

class Consumer2 implements Consumer {
    @Override
    public void consumer() {
        System.out.println("Consumer2");
    }
}

class Consumer3 implements Consumer {
    @Override
    public void consumer() {
        System.out.println("Consumer3");
    }
}

abstract class AbstractFactory {
    public abstract Product getProduct(String product);
    public abstract Consumer getConsumer(String consumer);
}

class Factory extends AbstractFactory {

    @Override
    public Product getProduct(String product) {
        if(product == null){
            return null;
        }else if(product.equalsIgnoreCase("product1")){
            return new Product1();
        }else if(product.equalsIgnoreCase("product2")){
            return new Product2();
        }else if(product.equalsIgnoreCase("product3")){
            return new Product3();
        }
        return null;
    }

    @Override
    public Consumer getConsumer(String consumer) {
        return null;
    }
}

class Consume extends AbstractFactory {
    @Override
    public Product getProduct(String product) {
        return null;
    }

    @Override
    public Consumer getConsumer(String consumer) {
        if(consumer == null){
            return null;
        }else if(consumer.equalsIgnoreCase("consumer1")){
            return new Consumer1();
        }else if(consumer.equalsIgnoreCase("consumer2")){
            return new Consumer2();
        }else if(consumer.equalsIgnoreCase("consumer3")){
            return new Consumer3();
        }
        return null;
    }
}

class FactoryProducer {
    public static AbstractFactory getFactory(String choice){
        if(choice.equalsIgnoreCase("Producer")){
            return new Factory();
        } else if(choice.equalsIgnoreCase("Consumer")){
            return new Consume();
        }
        return null;
    }
}

