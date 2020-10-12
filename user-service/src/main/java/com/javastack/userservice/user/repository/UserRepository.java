
package com.javastack.userservice.user.repository;


import com.javastack.userservice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户信息repository
 */
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryEx {

}
