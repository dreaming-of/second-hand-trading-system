package com.chl.campussecondhandtradingsystem.service;

import com.chl.campussecondhandtradingsystem.dao.IUserDao;
import com.chl.campussecondhandtradingsystem.pojo.User;
import com.chl.campussecondhandtradingsystem.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private IUserDao userDao;

    public List<User> findAllUser(){
        return userDao.findAllUser();
    }

    public User findUser(User user){
        user.setPassword(MD5Utils.md5(user.getPassword()));
        return userDao.findUser(user);
    }

    public User findAdmin(User user){
        user.setPassword(MD5Utils.md5(user.getPassword()));
        return userDao.findUser(user);
    }

    public int insertUser(User user) {
        user.setPassword(MD5Utils.md5(user.getPassword()));
        user.setStatus(0);
        user.setHeaderImg(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        return userDao.insertUser(user);
    }

    public Map<String, Object> findUserByStudentNumber(String student_number){
        Map<String, Object> res =  new HashMap<>();
        res.put("flag" ,userDao.findUserByStudentNumber(student_number) == 1);
        return res;
    }

    public User findUserById(int id){
        return userDao.findUserById(id);
    }

    public void updateHeader(int user_id, String headerUrl) {
        userDao.updateHeader(user_id, headerUrl);
    }

    public void updatePassword(User user) {
        userDao.updatePassword(user);
    }

    public void changeProfile(User user) {
        userDao.changeProfile(user);
    }

    public void deleteUserById(int user_id) {
        userDao.deleteUserById(user_id);
    }

    public void upgrateUserById(int user_id) {
        userDao.upgrateUserById(user_id);
    }
}
