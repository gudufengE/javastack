package com.javastack.userservice.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


/**
 * 用户消息发送者
 */
@Component
public class UserMsgSender {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    //消息发送接口
    private Source source;

    @Autowired
    public UserMsgSender(Source source) {
        this.source = source;
    }

    public void sendMsg(UserMsg userMsg) {

        System.out.println("发送用户【"+userMsg.getAction()+"】消息" + userMsg);
        this.logger.info("发送用户【"+userMsg.getAction()+"】消息:{} ", userMsg);

        // 发送消息
        this.source.output().send(MessageBuilder.withPayload(userMsg).build());
    }
}
