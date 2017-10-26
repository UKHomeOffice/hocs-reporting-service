package com.sls.listService;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ListServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ListServiceApplication.class, args);
    }
}
