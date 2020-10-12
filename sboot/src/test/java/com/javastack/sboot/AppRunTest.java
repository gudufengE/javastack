package com.javastack.sboot;

import com.javastack.publiccomponent.utils.IdUtils;
import com.javastack.sboot.admin.model.User;
import com.javastack.sboot.admin.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AppRunTest {
    @Autowired
    private UserService userService;

    @Test
    public void test() {
        System.out.println(IdUtils.getOrderSnByTime18());
//        List<User> users = userService.queryAllUsers();
//        for (User user : users) {
//            System.out.println(user.getName());
//        }
    }
}
