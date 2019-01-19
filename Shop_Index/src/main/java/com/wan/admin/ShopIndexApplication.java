package com.wan.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.wan")
public class ShopIndexApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopIndexApplication.class, args);
    }

}

