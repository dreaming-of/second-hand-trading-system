package com.chl.campussecondhandtradingsystem.dao;

import com.chl.campussecondhandtradingsystem.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserDao {
    List<User> findAllUser();

    User findUser(User user);

    int insertUser(User user);

    int findUserByStudentNumber(String student_number);

    User findUserById(int id);

    void updateHeader(int id, String headerImg);
}
