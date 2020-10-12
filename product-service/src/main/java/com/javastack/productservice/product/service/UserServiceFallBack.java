package com.javastack.productservice.product.service;

import com.javastack.productservice.product.api.UserDto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceFallBack implements UserService {

    @Override
    public List<UserDto> findAll() {
        List<UserDto> userDtoList = new ArrayList<>();
        UserDto userDto = new UserDto(100L, "Anonymous1", "/default/100.png");
        UserDto userDto2 = new UserDto(101L, "Anonymous2", "/default/101.png");
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
