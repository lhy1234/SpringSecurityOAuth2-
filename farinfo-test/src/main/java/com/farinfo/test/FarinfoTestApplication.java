package com.farinfo.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@SpringBootApplication
public class FarinfoTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(FarinfoTestApplication.class, args);
        System.err.println("========= test =============");
    }

}
