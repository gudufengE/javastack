package com.javastack.sboot.admin.controller;


import com.javastack.publiccomponent.utils.NumberUtil;
import com.javastack.publiccomponent.utils.Result;
import com.javastack.publiccomponent.utils.ResultCodeEnum;
import com.javastack.sboot.admin.model.User;
import com.javastack.sboot.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validator;
import java.awt.*;

@Slf4j
@Controller
@RequestMapping(value = "/user")
public class UserController {

//    @Autowired
//    RedisTemplate redisTemplate;  //k-v, k、v都是对象
//    @Autowired
//    StringRedisTemplate stringRedisTemplate;  //k-v, k、v都要求字符串

    @Autowired
    Validator validator;

    @Autowired
    private UserService userService;


    //加@ResponseBody，这里返回对象，容器自动帮我们装换为JSON格式的字符串fa回  或 XML 或String 形式数据
    @GetMapping(value = "/get")
    @ResponseBody
    public User user() {
        return new User("1000", "demo", "3721", "888888", "100");
    }

    @GetMapping(value = "/get2")
    @ResponseBody
    public String user2() {
        return "你好-2020-yes";
    }

    ///////////////////////////////////////////////////////////////////
    //query
    //不加@ResponseBody，则返回一个视图
    @RequestMapping(value = {"", "/"})
    public String index() {
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String queyUserList(Model model) {
        model.addAttribute("users", userService.queryAllUsers());
        return "/user/list";
    }

    ///////////////////////////////////////////////////////////////////
    //add
    @RequestMapping("/toadd")
    public String toadd() {
        return "user/useradd";
    }

    @RequestMapping("/add")
    public String submitadd(User user) {
        boolean b = userService.isExistsName(user.getName());
        if (b) {
            System.out.println(user.getName() + "已经存在");
            return "redirect:/user/list";
        }
        boolean ret = userService.createUser(user);
        return "redirect:/user/list";
    }

    ///////////////////////////////////////////////////////////////////
    //edit
    @RequestMapping("/toedit")
    public String toedit(Model model, String id) {
        model.addAttribute("user", userService.getUser(id));
        return "/user/useredit";
    }

    //表单提交
    @RequestMapping("/edit")
    public String edit(User user) {
        boolean ret = userService.updateUser(user);
        return "redirect:/user/list";
    }

    //使用HttpServletRequest 或 @RequestParam
    @RequestMapping("/editEx")
    @ResponseBody
//    public Result editEx(HttpServletRequest request) {
    public Result editEx(@RequestParam("userid") String userid,
                         @RequestParam("name") String name,
                         @RequestParam("qq") String qq,
                         @RequestParam("age") String age) {
//        //获取数据
//        String userid = request.getParameter("userid");
//        String name = request.getParameter("name");
//        String qq = request.getParameter("qq");
//        String age = request.getParameter("age");
        User u = new User(userid, name, qq, userService.default_password, age);
        System.out.println(u.toString());

        System.out.println();
        //校验
        //名称可以不修改，但修改了就要求不重复
        User oldUser = userService.getUser(userid);
        if (oldUser != null && oldUser.getName() != null && !oldUser.getName().equals(name)) {  //修改了名称
            if (userService.isExistsName(name)) {
                return Result.failure(ResultCodeEnum.PARAM_ERROR).setMsg("姓名["+name+"]被占用了");
            }
        } else {//名称没有修改，pass
            //------
        }

        if (!NumberUtil.isNumeric(age) || (Integer.parseInt(age) < 0 || Integer.parseInt(age) > 150)) {
//            return Result.failure(ResultCodeEnum.PARAM_ERROR);
            return Result.failure(ResultCodeEnum.PARAM_ERROR).setMsg("年龄参数非法");
        }
//        stringRedisTemplate.opsForValue().set("p", "10000");
        userService.updateUser(new User(userid, name, qq, userService.default_password, age));
        //返回
        return Result.success().addExtra("url", "/user/list");
    }


    //get请求，使用@PathVariable
    @GetMapping("/editEx2/{userid}/{name}/{qq}/{age}")
    @ResponseBody
    public Result editEx2(@PathVariable("userid") String userid,
                          @PathVariable("name") String name,
                          @PathVariable("qq") String qq,
                          @PathVariable("age") String age) {
        User user = new User(userid, name, qq, userService.default_password,age);
        System.out.println(user.toString());

        return Result.success().addExtra("url", "/user/list");

    }


    //使用@RequestBody，一般是post请求--》推荐的做法
    @RequestMapping("/editExEx")
//    @RequestMapping(value = "/editExEx", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result editExEx(@RequestBody @Validated User user) {

        System.out.println(user.toString());

        //....
        //返回
        return Result.success().addExtra("url", "/user/list");

    }


    ///////////////////////////////////////////////////////////////////
    //del
    @RequestMapping("/delete")
    public String deluser(String id) {
        boolean ret = userService.delete(id);
        return "redirect:/user/list";
    }


    ///////////////////////////////////////////////////////////////////





}
