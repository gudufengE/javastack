package com.javastack.sboot.admin.interceptor;

import com.javastack.sboot.admin.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求地址拦截器实现的两种方法：
 * 一、继承HandlerInterceptorAdapter类，如本类就是
 * 二、实现HandlerInterceptor接口
 */
@Component
public class UserLoginHandleInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("-----自定义拦截器-----");

        boolean flag = true;
        User user = (User) request.getSession().getAttribute("cUser");
        if (null == user) {
            response.sendRedirect("/");
            flag = false;
        } else {
            flag = true;
        }

        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
