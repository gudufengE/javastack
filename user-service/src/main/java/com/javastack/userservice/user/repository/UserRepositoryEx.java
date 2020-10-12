
package com.javastack.userservice.user.repository;

import com.javastack.userservice.user.entity.User;

import java.util.List;

/**
 * 用户信息管理扩展repository
 */
public interface UserRepositoryEx {
    List<User> findTopUser(int maxResult);
}
