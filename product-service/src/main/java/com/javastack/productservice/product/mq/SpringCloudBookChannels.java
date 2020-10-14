package com.javastack.productservice.product.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 自定义一个消息通道
 */
public interface SpringCloudBookChannels {
    @Input("inboundUserMsg")
    SubscribableChannel userMsgsReceiver();

    @Output("outboundProductMsg")
    MessageChannel productMsgSender();
}
