package com.javastack.zipkin2service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import zipkin2.server.internal.EnableZipkinServer;
//import zipkin.server.EnableZipkinServer;

//@EnableEurekaClient
@EnableZipkinServer
@SpringBootApplication
public class Zipkin2serviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Zipkin2serviceApplication.class, args);
    }

}
