package com.example.cbbshop.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;

public class LoginInterceptor implements HandlerInterceptor, HandleInterceptor {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            setReturn(response, 401, "用户未登录，请先登录");
            return false;
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 后续操作
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清理操作
    }

    private void setReturn(HttpServletResponse response, Integer code, String msg) throws IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        Result result = new Result(code, msg, "");
        String json = objectMapper.writeValueAsString(result);
        response.getWriter().print(json);
    }

    public static class Result {
        private int code;
        private String message;
        private String data;

        public Result(int code, String message, String data) {
            this.code = code;
            this.message = message;
            this.data = data;
        }

        // Getter 和 Setter 省略
    }
}