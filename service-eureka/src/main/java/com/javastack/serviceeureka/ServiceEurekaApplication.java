package com.javastack.serviceeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ServiceEurekaApplication {

    public static void main(String[] args) {
//        args = new String[1];
//        args[0] = "--spring.profiles.active=sdpeer1";
        SpringApplication.run(ServiceEurekaApplication.class, args);
    }

}
