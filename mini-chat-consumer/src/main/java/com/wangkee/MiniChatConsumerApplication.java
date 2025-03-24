package com.wangkee;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Import(RocketMQAutoConfiguration.class)
public class MiniChatConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniChatConsumerApplication.class, args);
    }

}
