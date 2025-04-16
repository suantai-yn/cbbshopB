package com.example.cbbshop.dto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ChangePasswordRequest {

    @NotBlank(message = "旧密码不能为空。")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空。")
    @Size(min = 6, max = 15, message = "新密码长度应在6-15个字符之间，仅允许字母、数字和特殊符号。")
    @Pattern(regexp = "^[A-Za-z\\d!@#$%^&*]+$", message = "新密码只能包含字母、数字和特殊符号。")
    private String newPassword;

    // Getters and Setters
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}