package com.wangkee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
public class Api8890UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Api8890UserServiceApplication.class, args);
    }

}
