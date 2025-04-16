package com.example.cbbshop.config;

import com.example.cbbshop.interceptor.LoginInterceptor;  // 请根据您的项目实际包路径修改
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class DemoWebConfig implements WebMvcConfigurer {

    /**
     * 拦截器配置
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器
        registry.addInterceptor((HandlerInterceptor) new LoginInterceptor()).addPathPatterns("/api/**")
                // 放行路径，可以添加多个
                .excludePathPatterns("/api/login");
    }
}