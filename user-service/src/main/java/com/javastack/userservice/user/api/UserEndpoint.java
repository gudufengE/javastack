
package com.javastack.userservice.user.api;

import com.javastack.userservice.user.service.GitConfig;
import com.javastack.userservice.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 用户管理的Endpoint
 */
@RestController
@RequestMapping("/users")
@Api(tags = "用户信息", description = "用户信息", value = "用户信息")
public class UserEndpoint {
    @Autowired
    private UserService userService;

    @Autowired
    private GitConfig gitConfig;

    @ApiIgnore
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
    @ApiOperation(value="用户列表", position = 1, notes="用户列表")
    @ApiImplicitParams({
    })
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
    @ApiOperation(value="用户详情", position = 3, notes="用户详情")
    @ApiImplicitParams({
    })
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
    @ApiOperation(value="用户更新", position = 3, notes="用户更新")
    @ApiImplicitParams({
    })
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
    @ApiOperation(value="删除用户", position = 3, notes="删除用户")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable Long id) {
        this.userService.delete(id);
        return true;
    }
}
