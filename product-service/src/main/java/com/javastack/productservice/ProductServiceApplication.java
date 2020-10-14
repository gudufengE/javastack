package com.javastack.productservice;

import com.javastack.productservice.product.mq.SpringCloudBookChannels;
import com.javastack.productservice.product.mq.UserMsg;
import com.javastack.productservice.product.mq.UserMsgListener;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//@EnableBinding(Sink.class)
//@EnableBinding({SpringCloudBookChannels.class})
@EnableHystrixDashboard
@EnableHystrix
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class ProductServiceApplication {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @LoadBalanced
    @Bean(value = "restTemplate")
    RestTemplate restTemplate() {
        return new RestTemplate();
    }


//    https://www.cnblogs.com/mark7/p/8920288.html
//    com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet 的注释
    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }

//    @StreamListener(Sink.INPUT)
//    public void onUserMsgSink(UserMsg userMsg) {
//        System.out.println("接收到用户变更消息:"+userMsg);
//        this.logger.info("接收到用户变更消息: {}", userMsg);
//    }

//    @Autowired
//    private UserMsgListener userMsgListener;
//
//    @StreamListener("inboundUserMsg")
//    public void onUserMsgSink(UserMsg userMsg) {
//        userMsgListener.onUserMsgSink(userMsg);
//    }
}
