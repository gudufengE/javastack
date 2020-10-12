
package com.javastack.userservice.user.api;

import com.javastack.userservice.user.service.GitConfig;
import com.javastack.userservice.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理的Endpoint
 */
@RestController
@RequestMapping("/users")
public class UserEndpoint {
    @Autowired
    private UserService userService;

    @Autowired
    private GitConfig gitConfig;

    @RequestMapping("/testgitconfig")
    public String testconfig(){
        //能返回值，说明配置中心起作用了
        return gitConfig.getFoo();
    }

    /**
     * 获取用户列表
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<UserDto> findAll() {
        return this.userService.findAll();
    }

    /**
     * 获取用户详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserDto detail(@PathVariable Long id) {
        return this.userService.load(id);
    }

    /**
     * 更新用户详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public UserDto update(@PathVariable Long id, @RequestBody UserDto userDto) {
        return this.userService.save(userDto);
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable Long id) {
        this.userService.delete(id);
        return true;
    }
}
