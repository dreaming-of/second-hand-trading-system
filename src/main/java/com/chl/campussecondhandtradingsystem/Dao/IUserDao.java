package com.chl.campussecondhandtradingsystem.Dao;

import com.chl.campussecondhandtradingsystem.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserDao {
    User findUser(User user);

    int insertUser(User user);

    int findUserByStudentNumber(String id);
}
