package com.farinfo.benefit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class GongYiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GongYiApplication.class, args);
        System.err.println("=====  gongyi started !!!====");
    }

}
