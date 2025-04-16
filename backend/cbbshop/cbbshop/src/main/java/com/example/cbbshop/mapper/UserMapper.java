package com.example.cbbshop.mapper;

import com.example.cbbshop.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    User selectUserByUsername(@Param("username") String username);

    @Insert("INSERT INTO user (username, password, contact_number, preferred_location, reset_question, reset_answer, user_type) " +
            "VALUES (#{username}, #{password}, #{contactNumber}, #{preferredLocation}, #{resetQuestion}, #{resetAnswer}, #{userType})")
    int insertUser(User user);

    @Select("SELECT * FROM user WHERE username = #{username} AND password = #{password}")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Update("UPDATE user SET password = #{newPassword} WHERE username = #{username}")
    int updatePassword(@Param("username") String username, @Param("newPassword") String newPassword);

    @Update("UPDATE user SET contact_number = #{contactNumber}, preferred_location = #{preferredLocation}, " +
            "reset_question = #{resetQuestion}, reset_answer = #{resetAnswer},password=#{password} WHERE username = #{username}")
    int updateUserInfo(User user);

    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);


    @Update("UPDATE user SET contact_number = #{contactNumber}, preferred_location = #{preferredLocation}, " +
            "reset_question = #{resetQuestion}, reset_answer = #{resetAnswer} WHERE username = #{username}")
    int updateUserInfoPartial(@Param("username") String username,
                              @Param("contactNumber") String contactNumber,
                              @Param("preferredLocation") String preferredLocation,
                              @Param("resetQuestion") String resetQuestion,
                              @Param("resetAnswer") String resetAnswer);


    // 获取所有 'buyer' 类型的用户
    @Select("SELECT * FROM User WHERE user_type = 'buyer'")
    List<User> findUsersByType(String userType);

    // 通过用户ID查询用户
    @Select("SELECT * FROM User WHERE id = #{id}")
    User findUserById(Integer id);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(@Param("id") Integer id);
}