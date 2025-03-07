package com.wangkee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class Api8891FileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Api8891FileServiceApplication.class, args);
    }

}
