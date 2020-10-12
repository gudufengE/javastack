package com.javastack.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

@EnableEurekaClient
//@EnableBinding 让Spring Cloud Stream启用一个消息代理，同时绑定到Source接口所定义的消息通道（output）中，通过该通道就可以实现用户变更消息的发送了
@EnableBinding(Source.class)
@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
