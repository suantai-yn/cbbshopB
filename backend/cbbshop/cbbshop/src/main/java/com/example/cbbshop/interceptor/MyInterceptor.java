package com.example.cbbshop.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;  // 使用jakarta.servlet
import jakarta.servlet.http.HttpServletResponse; // 使用jakarta.servlet

@Component
public class MyInterceptor implements HandlerInterceptor { // 修复拼写错误

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 在请求处理之前执行
        System.out.println("Pre Handle: " + request.getRequestURI());
        // 返回 true 继续请求处理，返回 false 停止请求
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 在请求处理之后执行
        System.out.println("Post Handle: " + request.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在请求完成后执行
        if (ex != null) {
            System.out.println("Error occurred: " + ex.getMessage());
        }
        System.out.println("After Completion: " + request.getRequestURI());
    }
}