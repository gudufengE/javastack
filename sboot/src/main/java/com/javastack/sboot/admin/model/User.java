package com.javastack.sboot.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userid;
    private String name;
    private String qq;
    private String password;

    @NotNull
    @Length(min = 1, max = 149, message = "年龄范围：大于0小于150")
    private String age;

}
