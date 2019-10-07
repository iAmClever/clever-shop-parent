package com.clever;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by clever on 2019/10/2.
 */
@SpringBootApplication
@MapperScan(basePackages = "com.clever.mapper")
public class AppIntegral {
    public static void main(String[] args) {
        SpringApplication.run(AppIntegral.class,args);
    }
}
