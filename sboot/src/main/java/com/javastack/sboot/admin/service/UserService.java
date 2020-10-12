package com.javastack.sboot.admin.service;


//import com.javastack.sboot.admin.Utils.IdUtils;
import com.javastack.publiccomponent.utils.IdUtils;
import com.javastack.sboot.admin.model.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private static List<User> users = new ArrayList<>();
    public final String default_password = "123456";

    static {
        users = new ArrayList<User>();

        for (int i = 0; i < 5; i++) {
            String id = IdUtils.id();
            User u = new User(id, "admin" + i, IdUtils.random(10000) + "", "123456", IdUtils.random(149) + "");
            users.add(u);
        }
    }

    public List<User> queryAllUsers() {
        return users;
    }

    public boolean createUser(User user) {

        try {
            user.setUserid(IdUtils.id());
            user.setPassword(default_password);
            users.add(user);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean updateUser(User user) {
        try {
            users.remove(getUser(user.getUserid()));
            users.add(user);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return true;

    }

    public User findUserByName(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    /**
     * @param name
     * @return 空或重名 返回true
     */
    public boolean isExistsName(String name) {
        if (StringUtils.isEmpty(name)) {
            return true;
        }

        User user = findUserByName(name);
        if (user != null) {
            return true;
        }

        return false;
    }

    public User getUser(String userid) {
        if (StringUtils.isEmpty(userid)) {
            return null;
        }

        for (User user : users) {
            if (user.getUserid().equals(userid)) {
                return user;
            }
        }

        return null;
    }


    public boolean delete(String id) {
        try {
            users.remove(getUser(id));
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }
}
