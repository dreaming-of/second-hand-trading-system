package com.chl.campussecondhandtradingsystem.dao;

import com.chl.campussecondhandtradingsystem.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IUserDao {
    List<User> findAllUser();

    User findUser(User user);

    User findAdmin(User user);

    int insertUser(User user);

    int findUserByStudentNumber(String student_number);

    User findUserById(int id);

    void updateHeader(@Param("user_id") int id, @Param("headerImg") String headerImg);

    void updatePassword(User user);

    void changeProfile(User user);

    void deleteUserById(int user_id);
}
