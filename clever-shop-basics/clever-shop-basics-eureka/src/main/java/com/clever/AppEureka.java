package com.clever;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * eureka服务
 */
@SpringBootApplication
@EnableEurekaServer
@EnableApolloConfig
public class AppEureka   {
    public static void main(String[] args) {
        SpringApplication.run(AppEureka.class,args);
    }
}
