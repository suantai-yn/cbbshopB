package com.example.cbbshop.utils;

import jakarta.servlet.http.HttpServletRequest; // 确保使用 Jakarta 版本
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpContextUtil {

    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("No current request context available");
        }
        return attributes.getRequest();
    }

    public static String getDomain() {
        HttpServletRequest request = getHttpServletRequest();
        String scheme = request.getScheme(); // http or https
        String serverName = request.getServerName(); // www.example.com
        int serverPort = request.getServerPort(); // port number
        String port = (serverPort == 80 || serverPort == 443) ? "" : ":" + serverPort; // Only append port if not default

        return scheme + "://" + serverName + port;
    }

    public static String getOrigin() {
        HttpServletRequest request = getHttpServletRequest();
        String origin = request.getHeader("Origin");
        return (origin != null) ? origin : "unknown"; // 返回一个默认值
    }
}