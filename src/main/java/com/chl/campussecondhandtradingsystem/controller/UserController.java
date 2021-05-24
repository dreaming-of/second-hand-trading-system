package com.chl.campussecondhandtradingsystem.controller;

import com.chl.campussecondhandtradingsystem.pojo.User;
import com.chl.campussecondhandtradingsystem.service.UserService;
import com.chl.campussecondhandtradingsystem.utils.MD5Utils;
import com.chl.campussecondhandtradingsystem.utils.MyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Value("${trading.path.upload}")
    private String uploadPath;

    @Value("${trading.path.domain}")
    private String domain;

    @PostMapping("/login")
    public String login(User user, Model model, HttpSession session) {
        User u = userService.findUser(user);
        if (u == null){
            model.addAttribute("msg", "用户名或密码错误");
            return "login";
        }else {
            session.setAttribute("LoginUser", u);
            return "redirect:/index";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("LoginUser");
        return "login";
    }

    @PostMapping("/register")
    public String insertUser(User user){
        userService.insertUser(user);
        return "redirect:/login.html";
    }

    @GetMapping("/findUserByStudentNumber")
    @ResponseBody
    public Map<String, Object> findUserByStudentNumber(String student_number){
        return userService.findUserByStudentNumber(student_number);
    }

    @PostMapping("/upload")
    public String upload(MultipartFile headerImage, Model model, HttpSession session){
        String originalFilename = headerImage.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = MyUtils.getUUID() + suffix;
        File dest = new File(uploadPath + "/" + newFilename);
        try {
            headerImage.transferTo(dest);
        } catch (IOException e) {
            log.error("上传文件失败： " + e.getMessage());
            throw new RuntimeException("上传文件失败");
        }
        User user = (User) session.getAttribute("LoginUser");
        String headerImg = domain + "/header/" + newFilename;
        String oldHeader = uploadPath + user.getHeaderImg().substring(user.getHeaderImg().lastIndexOf("/"));
        File f = new File(oldHeader);
        user.setHeaderImg(headerImg);
        userService.updateHeader(user.getUser_id(), user.getHeaderImg());
        f.delete();
        return "redirect:/setting";
    }

    @PostMapping("/changePwd")
    public String changePwd(@RequestParam("oldpwd") String oldPwd,
                            @RequestParam("newpwd") String newPwd, Model model,
                            HttpSession session){
        User user = (User) session.getAttribute("LoginUser");
        oldPwd = MD5Utils.md5(oldPwd);
        if (!user.getPassword().equals(oldPwd)){
            model.addAttribute("msg","原密码错误！");
            return "setting";
        }
        user.setPassword(MD5Utils.md5(newPwd));
        userService.updatePassword(user);
        return "redirect:/logout";
    }

    @GetMapping("/header/{fileName}")
    public void getHeader(@PathVariable("fileName")String filename, HttpServletResponse response){
        filename = uploadPath + "/" + filename;
        String suffix = filename.substring(filename.lastIndexOf('.'));
        response.setContentType("image/" + suffix);
        try(
                OutputStream out = response.getOutputStream();
                FileInputStream in = new FileInputStream(filename)
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while((b = in.read(buffer)) != -1){
                out.write(buffer,0, b);
            }
        } catch (IOException e) {
            log.error("获取头像失败: " + e.getMessage());
        }
    }

    @PostMapping("/changeProfile")
    public String changeProfile(User user, HttpSession session){
        User loginUser = (User) session.getAttribute("LoginUser");
        loginUser.setUsername(user.getUsername());
        loginUser.setPhone_number(user.getPhone_number());
        loginUser.setEmail(user.getEmail());
        userService.changeProfile(loginUser);
        session.setAttribute("LoginUser", loginUser);
        return "setting";
    }
}