
package com.javastack.userservice.user.service;

import com.javastack.publiccomponent.utils.IdUtils;
import com.javastack.userservice.user.api.UserDto;
import com.javastack.userservice.user.entity.User;
import com.javastack.userservice.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 用户管理服务实现
 */
@Service
public class UserService {
    private static List<User> users = new ArrayList<>();

    @Value("${server.port}")
    private int userservicePort = 0;
    static {
        users = new ArrayList<User>();

        for (int i = 0; i < 15; i++) {
            String id = IdUtils.id();
            User u = new User("name_"+id, "http://www.baidu.com/avatar/"+id);
            u.setId(Long.parseLong(i + ""));
            users.add(u);
        }
    }
//    @Autowired
//    protected UserRepository userRepository;

    public List<UserDto> findAll() {
//        List<User> users = this.userRepository.findAll();//TODO

        return this.users.stream().map((user) -> {
            return new UserDto(user, userservicePort);
        }).collect(Collectors.toList());
    }

    public UserDto load(Long id) {
//        User user = this.userRepository.findOne(id); //todo
        User user =getUser(id + "");

        if (null == user)
            return null;

        return new UserDto(user, userservicePort);
    }

    public UserDto save(UserDto userDto) {

//        User user = this.userRepository.findOne(userDto.getId());  //todo
        User user = getUser(userDto.getId() + "");

        if (null == user) {
            user = new User(userDto.getNickname(), userDto.getAvatar());
        }

//        this.userRepository.save(user);  //TODO

        return new UserDto(user, userservicePort);
    }

    public void delete(Long id) {

//        this.userRepository.delete(id); //TODO
        users.remove(getUser(id + ""));
    }

    public User getUser(String userid) {
        if (StringUtils.isEmpty(userid)) {
            return null;
        }

        for (User user : users) {
            if (user.getId().toString().equals(userid)) {
                return user;
            }
        }

        return null;
    }
}
