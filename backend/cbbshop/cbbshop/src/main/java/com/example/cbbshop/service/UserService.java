package com.example.cbbshop.service;

import com.example.cbbshop.mapper.UserMapper;
import com.example.cbbshop.model.User;
import com.example.cbbshop.model.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper; // 保留这行

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public boolean register(User user) {
        // 检查用户名是否已存在
        User existingUser = userMapper.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new IllegalArgumentException("用户名已存在");
        }
        System.out.println("注册的联系号码: " + user.getContactNumber());  // 打印出传入的号码
        // 校验联系电话
        if (user.getContactNumber() == null || !user.getContactNumber().matches("\\d{11}")) {
            throw new IllegalArgumentException("联系号码必须是11位数字。");
        }

        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 插入用户
        int rowsInserted = userMapper.insertUser(user);
        return rowsInserted == 1; // 如果插入了1行数据，返回true
    }

    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        // 如果用户存在，则验证密码
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user; // 登录成功，返回用户对象
        }
        return null; // 登录失败，返回 null
    }

    public boolean verifyPassword(String username, String oldPassword) {
        User user = userMapper.findByUsername(username);
        return user != null && passwordEncoder.matches(oldPassword, user.getPassword());
    }


    public void updateUserBasicInfo(User userUpdate) {
        User existingUser = userMapper.findByUsername(userUpdate.getUsername());

        if (existingUser != null) {
            // 更新现有用户的数据
            if (userUpdate.getContactNumber() != null) {
                existingUser.setContactNumber(userUpdate.getContactNumber());
            }
            if (userUpdate.getPreferredLocation() != null) {
                existingUser.setPreferredLocation(userUpdate.getPreferredLocation());
            }
            if (userUpdate.getResetQuestion() != null) {
                existingUser.setResetQuestion(userUpdate.getResetQuestion());
            }
            if (userUpdate.getResetAnswer() != null) {
                existingUser.setResetAnswer(userUpdate.getResetAnswer());
            }
            if (userUpdate.getPassword() != null) {
                existingUser.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
            }

            // 记录更新的结果
            int updatedRows = userMapper.updateUserInfo(existingUser);
            if (updatedRows == 0) {
                throw new RuntimeException("没有用户记录被更新，可能是因为用户不存在或数据没有变化。");
            }
        } else {
            throw new IllegalArgumentException("用户不存在");
        }
    }

    public String getResetQuestion(String username) {
        User user = userMapper.findByUsername(username);
        // 添加调试输出
        System.out.println("请求的用户名: " + username);
        System.out.println("查询到的用户: " + user);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在。");
        }

        String question = user.getResetQuestion();
        // 输出验证问题
        System.out.println("返回的验证问题: " + question);

        // 如果验证问题为空，可以返回一个提示信息
        if (question == null) {
            return "用户没有设置验证问题";
        }

        return question; // 返回重置密码的验证问题
    }

    public boolean resetPassword(Integer customerId, String newPassword) {
        // 根据 customerId 查找用户
        User user = userMapper.findById(customerId); // 假设 findById 方法根据 customerId 查找用户
        if (user == null) {
            throw new IllegalArgumentException("用户不存在。");
        }

        // 对新密码进行验证（可以根据需求做更复杂的验证）
        if (newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException("新密码必须至少6个字符。");
        }

        // 更新密码
        String encodedPassword = passwordEncoder.encode(newPassword); // 对密码进行加密
        userMapper.updatePassword(user.getUsername(), encodedPassword); // 更新密码，假设 updatePassword 方法是按用户名更新
        return true; // 密码重置成功
    }


    public void validateNewPassword(String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("新密码不能为空。");
        }
        if (newPassword.length() < 6 || newPassword.length() > 15) {
            throw new IllegalArgumentException("新密码长度应在6-15个字符之间。");
        }
        // 使用正则表达式检查新密码
        if (!newPassword.matches("^[A-Za-z\\d!@#$%^&*]+$")) {
            throw new IllegalArgumentException("新密码只能包含字母、数字和特殊符号。");
        }
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        User user = userMapper.findByUsername(username);
        if (user != null) {
            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                String encodedPassword = passwordEncoder.encode(newPassword);
                userMapper.updatePassword(username, encodedPassword);
                return true; // 密码修改成功
            } else {
                throw new IllegalArgumentException("旧密码不正确");
            }
        } else {
            throw new IllegalArgumentException("用户不存在");
        }
    }

    public User getUserById(Integer userId) {
        return userMapper.findUserById(userId);
    }

    public UserType getUserType(Integer userId) {
        User user = userMapper.findUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return user.getUserType();  // 返回 user_type
    }

    public List<User> getAllBuyers() {
        return userMapper.findUsersByType("buyer");
    }

    public User getUserInfoById(Integer id) {
        return userMapper.findById(id);
    }

    public boolean resetPassword_2(String username, String answer, String newPassword) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在。");
        }
        if (!user.getResetAnswer().equals(answer)) {
            return false; // 答案错误
        }
        // 更新密码
        String encodedPassword = passwordEncoder.encode(newPassword);
        userMapper.updatePassword(username, encodedPassword);
        return true; // 密码重置成功
    }
}

