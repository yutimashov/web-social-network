package com.getjavajob.dataprocessingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableFeignClients
@EnableRedisHttpSession
@SpringBootApplication
public class DataProcessingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataProcessingServiceApplication.class, args);
    }

}
