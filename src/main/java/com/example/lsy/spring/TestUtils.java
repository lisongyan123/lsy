package com.example.lsy.spring;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestUtils {
    //  @Bean(name="testDemo")
    @Bean
    public CartDTO said() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setProductID(789);
        cartDTO.setProductQuantity(10);
        return cartDTO;
    }

    @Data
    public class CartDTO {
        Integer productID;
        Integer productQuantity;
    }

} 