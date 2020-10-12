package com.javastack.zipkinservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import zipkin2.server.internal.EnableZipkinServer;

@SpringBootApplication
//@EnableEurekaClient
//通过@EnableZipkinServer开启zipkin服务器
@EnableZipkinServer
public class ZipkenApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZipkenApplication.class, args);
    }
}
