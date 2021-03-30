package com.chl.campussecondhandtradingsystem.Service;

import com.chl.campussecondhandtradingsystem.Dao.IUserDao;
import com.chl.campussecondhandtradingsystem.Utils.MD5Utils;
import com.chl.campussecondhandtradingsystem.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private IUserDao userDao;

    public User findUser(User user){
        user.setPassword(MD5Utils.md5(user.getPassword()));
        return userDao.findUser(user);
    }

    public int insertUser(User user) {
        user.setPassword(MD5Utils.md5(user.getPassword()));
        user.setHeaderImg(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        return userDao.insertUser(user);
    }

    public Map<String, Object> findUserByStudentNumber(String student_number){
        Map<String, Object> res =  new HashMap<>();
        res.put("flag" ,userDao.findUserByStudentNumber(student_number));
        return res;
    }

    public User findUserById(int id){
        return userDao.findUserById(id);
    }

    public void updateHeader(int user_id, String headerUrl) {
        userDao.updateHeader(user_id, headerUrl);
    }
}
