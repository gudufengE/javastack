package com.javastack.sboot.admin.controller;


import com.javastack.publiccomponent.utils.Result;
import com.javastack.publiccomponent.utils.ResultCodeEnum;
import com.javastack.sboot.admin.model.User;
import com.javastack.sboot.admin.service.LoginService;
import com.javastack.sboot.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"", "/"})
    public String tologin() {
        return "/index/login";
    }

    @RequestMapping("/login")
    @ResponseBody
    public Result login(@RequestParam String name, @RequestParam String password, HttpServletRequest request, Model model) {

        User currentUser = userService.findUserByName(name);

        if (currentUser == null) {
            return Result.failure(ResultCodeEnum.USER_NOT_EXISTS);
        }

        request.getSession(true).setAttribute("cUser", currentUser);
        model.addAttribute("cUser", currentUser);
        return Result.success().addExtra("url", "/user");
    }
}
