package com.farinfo.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableZuulProxy //网关
@SpringBootApplication
public class GatewayApplication {


    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }


    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
        System.err.println("========= gateway started !!! ========");
    }

}
