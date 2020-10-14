package com.javastack.productservice.product.mq;

import com.javastack.productservice.product.api.UserDto;
import com.javastack.productservice.product.redis.UserRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;


/**
 * 用户消息监听器
 *
 * @author CD826(CD826Dong@gmail.com)
 * @since 1.0.0
 */
@EnableBinding({SpringCloudBookChannels.class})
//@Component
public class UserMsgListener {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected UserRedisRepository userRedisRepository;

    @StreamListener("inboundUserMsg")
    public void onUserMsgSink(UserMsg userMsg) {
        if (UserMsg.UA_UPDATE.equalsIgnoreCase(userMsg.getAction())) {
            this.logger.info("收到用户更新消息，所要更新用户的ID: {}", userMsg);
            UserDto userDto = new UserDto(userMsg.getUserId(), userMsg.getUser().getNickname(), userMsg.getUser().getAvatar());
            userRedisRepository.saveUser(userDto);
        } else if (UserMsg.UA_DELETE.equalsIgnoreCase(userMsg.getAction())) {
            this.logger.info("收到用户删除消息，所删除            this.userRedisRepository.saveUser(userDto);\n用户的ID: {}", userMsg.getUserId());
            this.userRedisRepository.delete(userMsg.getUserId());
        } else {
            this.logger.info("收到未知用户消息，用户的ID: {}", userMsg.getUserId());
        }
    }
}