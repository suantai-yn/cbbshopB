package com.example.cbbshop.controller;

import com.example.cbbshop.model.User;
import com.example.cbbshop.service.UserService;
import com.example.cbbshop.dto.ChangePasswordRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

    private final UserService userService;

    @Autowired // 使用单个构造函数注入所有依赖
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        try {
            boolean isRegistered = userService.register(user);
            return ResponseEntity.ok("注册成功！");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());  // 返回详细错误信息
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("注册过程中发生错误。");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody User user, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            User loggedUser = userService.login(user.getUsername(), user.getPassword());

            if (loggedUser != null) {
                session.setAttribute("user", loggedUser);

                // 创建一个包含消息和用户类型的响应对象
                Map<String, Object> response = new HashMap<>();
                response.put("message", "登录成功！");
                response.put("userType", loggedUser.getUserType()); // 假设 getUserType() 返回用户类型

                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("message", "用户名或密码错误。");
                return ResponseEntity.badRequest().body(errorResponse);
            }
        } catch (Exception e) {
            // 打印异常信息
            e.printStackTrace();

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "登录过程中发生错误。" + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getCurrentUserProfile(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        // 检查用户是否已经登录
        if (session == null || session.getAttribute("user") == null) {
            return ResponseEntity.status(403).body("用户未登录，无法获取个人信息。"); // 返回403状态码和提示信息
        }

        User loggedUser = (User) session.getAttribute("user"); // 从会话中获取当前用户

        // 获取当前用户的完整信息
        User userProfile = userService.getUserInfoById(loggedUser.getId());
        if (userProfile == null) {
            return ResponseEntity.notFound().build(); // 用户未找到
        }

        // 创建一个只包含需要返回的信息的新的 User 对象
        User result = new User();
        result.setId(userProfile.getId());
        result.setUsername(userProfile.getUsername());
        result.setPreferredLocation(userProfile.getPreferredLocation());
        result.setUserType(userProfile.getUserType());
        result.setContactNumber(userProfile.getContactNumber());

        return ResponseEntity.ok(result); // 返回用户个人信息
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // 使会话无效
        return ResponseEntity.ok("注销成功！");
    }

    @GetMapping("/getResetQuestion")
    public ResponseEntity<String> getResetQuestion(@RequestParam String username) {
        try {
            String question = userService.getResetQuestion(username);
            return ResponseEntity.ok(question);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("获取验证问题失败: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("获取验证问题过程中发生错误。");
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam Integer customerId) {
        try {
            boolean isReset = userService.resetPassword(customerId, "123456");
            if (isReset) {
                return ResponseEntity.ok("密码已重置为 123456");
            } else {
                return ResponseEntity.badRequest().body("密码重置失败，客户不存在");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("密码重置过程中发生错误");
        }
    }

    @PostMapping("/resetPassword_2")
    public ResponseEntity<String> resetPassword_2(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String answer = payload.get("answer");
        String newPassword = payload.get("newPassword");
        // 对新密码进行简单验证（可以根据需求进行更复杂的验证）
        if (newPassword == null || newPassword.length() < 6) {
            return ResponseEntity.badRequest().body("新密码必须至少6个字符。");
        }
        try {
            boolean isReset = userService.resetPassword_2(username, answer, newPassword);
            return ResponseEntity.ok(isReset ? "密码重置成功！" : "密码重置失败，答案不正确。");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("密码重置失败: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("重置密码过程中发生错误。");
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest request, HttpServletRequest httpServletRequest) {
        try {
            // 验证用户是否已登录
            HttpSession session = httpServletRequest.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("用户未登录，无法修改密码。");
            }

            User loggedUser = (User) session.getAttribute("user");

            // 校验新密码的格式，使用 UserService 的方法
            userService.validateNewPassword(request.getNewPassword());

            // 调用 service 来更改密码
            boolean isChanged = userService.changePassword(loggedUser.getUsername(), request.getOldPassword(), request.getNewPassword());
            if (isChanged) {
                return ResponseEntity.ok("密码修改成功！");
            } else {
                return ResponseEntity.badRequest().body("旧密码不正确。");
            }
        } catch (IllegalArgumentException e) {
            // 捕获并返回详细的错误信息
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // 捕获其他异常并返回通用错误消息
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("修改密码过程中发生错误。");
        }
    }

    @PostMapping("/updateInfo")
    public ResponseEntity<Map<String, Object>> updateUserInfo(@Valid @RequestBody User userUpdate, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();

        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                response.put("success", false);
                response.put("message", "用户未登录，无法更新信息。");
                return ResponseEntity.status(403).body(response);
            }

            User loggedUser = (User) session.getAttribute("user");
            userUpdate.setUsername(loggedUser.getUsername()); // 保持用户名不变

            // 更新用户基本信息
            userService.updateUserBasicInfo(userUpdate);

            response.put("success", true);
            response.put("message", "用户信息更新成功！");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", "用户信息更新失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "更新用户信息过程中发生错误。");
            return ResponseEntity.status(500).body(response);
        }
    }


    // 获取所有买家信息
    @GetMapping("/buyers")
    public ResponseEntity<List<User>> getAllBuyers() {
        List<User> buyers = userService.getAllBuyers();
        return ResponseEntity.ok(buyers);
    }


}