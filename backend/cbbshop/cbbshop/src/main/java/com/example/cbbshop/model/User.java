package com.example.cbbshop.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;
import java.io.Serializable;
@Getter
@Setter
public class User implements Serializable {

    private Integer id;
    @Size(min = 1, max = 30, message = "用户名长度应在1-30个字符之间，仅允许文字、字母和下划线。")
    @Pattern(regexp = "^[a-zA-Z0-9_\\u4E00-\\u9FA5]+$", message = "用户名仅允许文字、字母和下划线。")
    private String username;

    // 密码
    @Size(min = 6, max = 15, message = "密码长度应在6-15个字符之间，仅允许字母、数字和特殊符号。")
    @Pattern(regexp = "^[A-Za-z\\d!@#$%^&*]+$", message = "密码只能包含字母、数字和特殊符号。")
    private String password;

    @Pattern(regexp = "^\\d{11}$", message = "联系电话格式不正确，必须是11位数字。")
    private String contactNumber;

    @Size(max = 200, message = "倾向交易地点的长度应不超过200个字符。")
    private String preferredLocation;

    @Size(min = 1, max = 30, message = "重置密码验证问题长度应在1-30个字符之间，仅允许文字与字母。")
    @Pattern(regexp = "^[a-zA-Z\\u4E00-\\u9FA5]+$", message = "重置密码验证问题仅允许文字与字母。")
    private String resetQuestion;

    @Size(min = 1, max = 20, message = "重置密码验证答案长度应在1-20个字符之间，仅允许文字与字母。")
    @Pattern(regexp = "^[a-zA-Z\\u4E00-\\u9FA5]+$", message = "重置密码验证答案仅允许文字与字母。")
    private String resetAnswer;

    // 使用 UserType 枚举
    private UserType userType; // 用户类型枚举

// Getters and Setters
}