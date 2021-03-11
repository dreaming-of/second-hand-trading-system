package com.chl.campussecondhandtradingsystem.Controller;

import com.chl.campussecondhandtradingsystem.pojo.User;
import com.chl.campussecondhandtradingsystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public String login(User user, Model model, HttpSession session) throws IOException {
        User u = userService.findUser(user);
        if (u == null){
            model.addAttribute("msg", "用户名或密码错误");
            return "login";
        }else {
            session.setAttribute("LoginUser", u);
            return "index";
        }
    }

    @GetMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("LoginUser");
        return "login";
    }

    @GetMapping("find")
    @ResponseBody
    public User find(User user, Model m){
        User user1 = userService.findUser(user);
        if(user1 == null){
            m.addAttribute("msg", "用户名或密码错误");
            user1 = new User();
        }
        return user1;
    }

    @PostMapping("register")
    public String insertUser(User user){
        userService.insertUser(user);
        return "login";
    }

    @GetMapping("findUserByStudentNumber")
    @ResponseBody
    public Map<String, Object> findUserByStudentNumber(String id){
        return userService.findUserByStudentNumber(id);
    }
}
