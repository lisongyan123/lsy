package com.example.lsy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Slf4j
@SpringBootApplication
@RestController
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
    public Function<Flux<String>, Flux<String>> lowerCase() {
        return flux -> flux.map(value -> value.toLowerCase());
    }
}
