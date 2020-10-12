
package com.javastack.productservice.product.service;


import com.javastack.productservice.product.api.UserDto;
//import org.springframework.cloud.netflix.feign.FeignClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import feign.Logger;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;


/**
 * 用户服务，使用Feign实现
 */
@FeignClient(value = "USERSERVICE",
//        fallback = UserService.UserServiceFallBack.class,
        /*  fallback = UserServiceFallBack.class,*/
        fallbackFactory = UserServiceFallBackFactory.class)
public interface UserService {

//    @RequestLine("GET /users")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    List<UserDto> findAll();

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    UserDto load(@PathVariable("id") Long id);
//
//    @Configuration
//    public class UserServiceHystrixConfig{
//        @Bean
//        Logger.Level feignLoggerLevel() {
//            return Logger.Level.FULL;
//        }
//    }

    @Component
    public class UserServiceFallBack implements UserService {

        @Override
        public List<UserDto> findAll() {
            UserDto userDto = new UserDto(100L, "Anonymous1", "/default/100.png");
            UserDto userDto2 = new UserDto(101L, "Anonymous2", "/default/100.png");

            List<UserDto> userDtoList = new ArrayList<>();
            userDtoList.add(userDto);
            userDtoList.add(userDto2);
            return userDtoList;
        }

        @Override
        public UserDto load(Long id) {
            UserDto userDto = new UserDto(100L, "Anonymous1", "/default/100.png");
            return userDto;
        }
    }
}
