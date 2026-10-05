package com.hotel.auth;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//@EnableEurekaClient
@EnableFeignClients
public class HotelAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelAuthServiceApplication.class, args);
    }

    @PostConstruct
    public void printWorkingDirectory() {
        System.out.println("Current directory: "
                + System.getProperty("user.dir"));
    }
}
