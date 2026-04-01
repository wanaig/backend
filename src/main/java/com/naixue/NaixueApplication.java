package com.naixue;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.naixue.mapper")
public class NaixueApplication {

    public static void main(String[] args) {
        SpringApplication.run(NaixueApplication.class, args);
    }
}
