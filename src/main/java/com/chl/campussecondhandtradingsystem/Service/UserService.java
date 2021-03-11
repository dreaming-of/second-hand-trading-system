package com.chl.campussecondhandtradingsystem.Service;

import com.chl.campussecondhandtradingsystem.Dao.IUserDao;
import com.chl.campussecondhandtradingsystem.Utils.MD5Utils;
import com.chl.campussecondhandtradingsystem.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(GoodsService.class);

    @Autowired
    private IUserDao userDao;

    public User findUser(User user){
        user.setPassword(MD5Utils.md5(user.getPassword()));
        return userDao.findUser(user);
    }

    public int insertUser(User user) {
        user.setPassword(MD5Utils.md5(user.getPassword()));
        return userDao.insertUser(user);
    }

    public Map<String, Object> findUserByStudentNumber(String id){
        Map<String, Object> res =  new HashMap<>();
        res.put("flag" ,userDao.findUserByStudentNumber(id));
        return res;
    }
}
