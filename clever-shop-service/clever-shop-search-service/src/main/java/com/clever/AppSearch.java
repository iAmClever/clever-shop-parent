package com.clever;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Created by clever on 2019/8/18.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2Doc
@EnableFeignClients
@EnableElasticsearchRepositories(basePackages = { "com.clever.esResposiory" })
public class AppSearch {
    public static void main(String[] args) {
        SpringApplication.run(AppSearch.class,args);
    }
}
