package com.javastack.productservice.product.service;

import feign.hystrix.FallbackFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFallBackFactory implements FallbackFactory<UserService> {

    @Autowired
    private UserServiceFallBack userServiceFallBack;

    @Override
    public UserService create(Throwable throwable) {
        throwable.printStackTrace();
        return userServiceFallBack;

//        return new UserService() {
//
//            @Override
//            public List<UserDto> findAll() {
//                UserDto userDto = new UserDto(100L, "Anonymous11", "/default/100.png");
//                UserDto userDto2 = new UserDto(101L, "Anonymous22", "/default/101.png");
//
//                List<UserDto> userDtoList = new ArrayList<>();
//                userDtoList.add(userDto);
//                userDtoList.add(userDto2);
//                return userDtoList;
//            }
//
//            @Override
//            public UserDto load(Long id) {
//                UserDto userDto = new UserDto(100L, "Anonymous11", "/default/100.png");
//                return userDto;
//            }
//
//        };
    }
}
