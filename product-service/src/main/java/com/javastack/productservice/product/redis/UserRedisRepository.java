package com.javastack.productservice.product.redis;

import com.javastack.productservice.product.api.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;


/**
 * 用户的Redis数据库服务
 */
@Repository
public class UserRedisRepository {
    public static final String USER_KEY_PRE = "user:";

//    @Autowired
//    @Qualifier("userRedisTemplate")
//    private RedisTemplate<String, UserDto> redisTemplate;
    private ValueOperations<String, UserDto> operations;

    @Autowired
    private RedisTemplate redisTemplate;  //k-v, k、v都是对象
//    @Autowired
//    StringRedisTemplate stringRedisTemplate;  //k-v, k、v都要求字符串

    @PostConstruct
    private void init() {
        this.operations = this.redisTemplate.opsForValue();
    }

    /**
     * 保存
     * @param userDto
     * @return
     */
    public void saveUser(UserDto userDto) {
        this.operations.set(this.buildKey(userDto.getId()), userDto);
    }

    /**
     * 加载
     * @param userId
     * @return
     */
    public UserDto findOne(Long userId) {
        String key = this.buildKey(userId);
        if (!this.redisTemplate.hasKey(key))
            return null;

        return this.operations.get(key);
    }

    /**
     * 删除
     * @param userId
     */
    public void delete(Long userId) {
        this.redisTemplate.delete(this.buildKey(userId));
    }

    /**
     * 构造存储的Key
     * @param userId
     * @return
     */
    protected String buildKey(Long userId) {
        return USER_KEY_PRE + String.valueOf(userId);
    }
}
