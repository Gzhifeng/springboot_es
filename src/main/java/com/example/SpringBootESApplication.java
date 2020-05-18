package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ganzhifeng
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.example.config", "com.example.controller", "com.example.util"})
public class SpringBootESApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootESApplication.class, args);
    }

}
