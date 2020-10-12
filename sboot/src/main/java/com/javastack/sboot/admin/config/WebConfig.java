package com.javastack.sboot.admin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javastack.sboot.admin.interceptor.UserLoginHandleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.List;

//https://juejin.im/post/6844903917344538637
@Configuration
//public class WebConfig extends WebMvcConfigurerAdapter {
    public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private UserLoginHandleInterceptor userLoginHandleInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginHandleInterceptor)
                .excludePathPatterns("", "/", "/login")  //排除登录
                .excludePathPatterns("/res/**") // 排除静态资源，在springboot 2.*.* 之前不要这行：https://blog.csdn.net/ln1593570p/article/details/80607616
                .excludePathPatterns("/actuator/**")
                .addPathPatterns("/**")  //所有的路径都加入拦截；既然是“所有”，没有这行，即代表“所有”，所以这行可有可无
        ;
    }

    //1.这个为解决中文乱码
    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }

    //2.1：解决中文乱码后，返回json时可能会出现No converter found for return value of type: xxxx
    //或这个：Could not find acceptable representation
    //解决此问题如下
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    //2.2：解决No converter found for return value of type: xxxx
    public MappingJackson2HttpMessageConverter messageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(getObjectMapper());
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        super.configureMessageConverters(converters);

        //解决中文乱码
        converters.add(responseBodyConverter());

        //解决： 添加解决中文乱码后的配置之后，返回json数据直接报错 500：no convertter for return value of type
        //或这个：Could not find acceptable representation
        converters.add(messageConverter());
    }

}
