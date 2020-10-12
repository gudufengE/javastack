package com.javastack.sboot;

import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

//@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@SpringBootApplication
//public class SbootApplication extends SpringBootServletInitializer {
public class SbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SbootApplication.class, args);
//        SpringApplication springApplication = new SpringApplication(SbootApplication.class);
//        springApplication.setBannerMode(Banner.Mode.OFF);
//        springApplication.run(args);
    }
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        //设置启动类，以便在独立的Tomcat上运行
//        return builder.sources(DemoApplication.class);
//    }

//    @Bean
//    public Validator validator() {
//        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
//                .configure()
//                // 快速失败模式
//                .failFast(true)
//                .buildValidatorFactory();
//        return validatorFactory.getValidator();
//    }
}
