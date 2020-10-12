package com.javastack.userservice.user.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class GitConfig {

    @Value("${myfoo}")
    private String foo;
}