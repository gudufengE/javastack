package com.javastack.sboot.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String userid;
    private String name;
    private String qq;
    private Integer age;

    @Override
    public String toString() {
        return "User{" +
                "userid='" + userid + '\'' +
                ", name='" + name + '\'' +
                ", qq='" + qq + '\'' +
                ", age=" + age +
                '}';
    }
}
