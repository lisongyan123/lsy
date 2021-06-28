package com.example.lsy;

import com.example.lsy.serverless.Foo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
@Slf4j
@SpringBootApplication
public class LsyApplication {

    public static void main(String[] args) {
        SpringApplication.run(LsyApplication.class, args);
    }
    @Bean
    public Function<String, String> reverseString() {
        return value -> new StringBuilder(value).reverse().toString();
    }

    @Bean
    public Function<String, String> uppercase() {
        return value -> value.toUpperCase();
    }

    @Bean
    public Supplier<Flux<Foo>> words() {
        return () -> Flux.fromArray(new Foo[]{new Foo("foo"), new Foo("bar")}).log();
    }

    @Bean
    public Function<Foo, List> word() {
        return ss -> {
            log.info("调用word成功，入参: "+ss.toString());
            return Arrays.asList(ss.getValue().split(","));
        };
    }

    @Bean
    public Function<Flux<String>, Flux<String>> lowerCase() {
        return flux -> flux.map(value -> value.toLowerCase());
    }

}
