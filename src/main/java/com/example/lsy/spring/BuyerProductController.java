package com.example.lsy.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * 买家商品 
 */ 
@RestController 
@RequestMapping("/buyer/product") 
public class BuyerProductController { 
  private static ApplicationContext applicationContext;

  @GetMapping(value = "/said")
  public TestUtils.CartDTO list(String str){
    TestUtils.CartDTO cartDTO = (TestUtils.CartDTO) SpringUtil.getBean("said");

    SpringUtil springUtil = (SpringUtil)SpringUtil.getBean(SpringUtil.class);
    System.out.println("springUtil:" + springUtil);
    return cartDTO;
  } 
} 