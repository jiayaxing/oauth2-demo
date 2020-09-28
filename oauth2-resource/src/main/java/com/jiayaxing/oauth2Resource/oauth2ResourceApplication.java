package com.jiayaxing.oauth2Resource;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class oauth2ResourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(oauth2ResourceApplication.class, args);
    }
}
