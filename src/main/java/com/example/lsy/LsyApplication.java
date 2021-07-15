package com.example.lsy;

import com.example.lsy.serverless.Foo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

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
    public Supplier<Flux<Foo>> words() {
        return () -> Flux.fromArray(new Foo[]{new Foo("foo"), new Foo("bar")}).log();
    }

    @Bean
    public Function<Foo, List> word() {
        return ss -> {
            log.info("调用word成功，入参: " + ss.toString());
            return Arrays.asList(ss.getValue().split(","));
        };
    }

    @Bean
    public Function<Foo, List> sys() {
        return ss -> {
            while (true) {
                System.out.println("啊");
            }
        };
    }

    @Bean
    public Function<Flux<String>, Flux<String>> lowerCase() {
        return flux -> flux.map(value -> value.toLowerCase());
    }

    @GetMapping("/hello1")
    public String getEcho() throws InterruptedException {
        Thread.sleep(1000);
        return "ok";
    }

    @GetMapping("/hello2")
    public Mono<String> hello() {
        return Mono.just("hello").delayElement(Duration.ofMillis(1000));
    }
}
