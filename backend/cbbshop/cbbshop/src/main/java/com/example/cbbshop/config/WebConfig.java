package com.example.cbbshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置全局 CORS 配置
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8086") // 允许来自该地址的请求
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的方法
                .allowedHeaders("*") // 允许的请求头
                .allowCredentials(true); // 允许携带凭证（如 Cookies）
    }
}
