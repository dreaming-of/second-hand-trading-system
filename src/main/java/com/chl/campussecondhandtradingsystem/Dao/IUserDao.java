package com.chl.campussecondhandtradingsystem.Dao;

import com.chl.campussecondhandtradingsystem.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserDao {
    User findUser(User user);

    int insertUser(User user);

    int findUserByStudentNumber(String student_number);

    User findUserById(int id);

    void updateHeader(int id, String headerImg);
}
