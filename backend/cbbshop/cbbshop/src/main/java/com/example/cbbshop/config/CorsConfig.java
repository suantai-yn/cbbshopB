package com.example.cbbshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig { // 合法的类名，不能以数字开头
    @Bean
    public CorsFilter corsFilter() {
        // 创建 CORS 配置源
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 允许源，这里允许所有源访问，实际应用中可以限制特定的来源
        corsConfiguration.addAllowedOrigin("*");
        // 允许所有请求头
        corsConfiguration.addAllowedHeader("*");
        // 允许所有方法
        corsConfiguration.addAllowedMethod("*");
        // 允许携带凭证
        corsConfiguration.setAllowCredentials(true);

        // 注册 CORS 配置到路径
        source.registerCorsConfiguration("/**", corsConfiguration);

        // 返回新的 CORS 过滤器
        return new CorsFilter(source);
    }
}