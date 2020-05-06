package com.farinfo.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


//注册中心 ，Eureka会暴露一些端点。
// 端点用于Eureka Client注册自身，获取注册表，发送心跳。
@EnableEurekaServer
@SpringBootApplication
public class RegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegisterApplication.class, args);
        System.err.println("======== register ===========");
    }

}
