package com.chl.campussecondhandtradingsystem.controller;

import com.chl.campussecondhandtradingsystem.pojo.Chat;
import com.chl.campussecondhandtradingsystem.service.ChatService;
import com.chl.campussecondhandtradingsystem.service.UserService;
import com.chl.campussecondhandtradingsystem.utils.MyUtils;
import com.chl.campussecondhandtradingsystem.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Value("${trading.path.upload}")
    private String uploadPath;

    @Value("${trading.path.domain}")
    private String domain;

    @PostMapping("login")
    public String login(User user, Model model, HttpSession session) {
        User u = userService.findUser(user);
        if (u == null){
            model.addAttribute("msg", "用户名或密码错误");
            return "login";
        }else {
            session.setAttribute("LoginUser", u);
            return "redirect:index";
        }
    }

    @GetMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("LoginUser");
        return "login";
    }

    @PostMapping("register")
    public String insertUser(User user){
        userService.insertUser(user);
        return "redirect:login";
    }

    @GetMapping("findUserByStudentNumber")
    @ResponseBody
    public Map<String, Object> findUserByStudentNumber(String student_number){
        return userService.findUserByStudentNumber(student_number);
    }

    @PostMapping("upload")
    public String upload(MultipartFile headerImage, Model model, HttpSession session){
        if (headerImage == null){
            model.addAttribute("error","还没选择文件");
            return "setting";
        }
        String originalFilename = headerImage.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)){
            model.addAttribute("error", "文件格式不确定");
            return "setting";
        }
        String newFilename = MyUtils.getUUID() + suffix;
        File dest = new File(uploadPath + "/" + newFilename);
        try {
            headerImage.transferTo(dest);
        } catch (IOException e) {
            log.error("上传文件失败： " + e.getMessage());
            throw new RuntimeException("上传文件失败");
        }
        User user = (User) session.getAttribute("LoginUser");
        String headerImg = domain + "/img/user/" + newFilename;
        userService.updateHeader(user.getUser_id(), headerImg);

        return "redirect:setting";
    }

    @GetMapping("chat/{user_id}")
    public String chat(@PathVariable("user_id") int userid, Model model, HttpSession session){
        User toUser = userService.findUserById(userid);
        model.addAttribute("toUser", toUser);
        User fromUser = (User) session.getAttribute("LoginUser");
        String chatId = MyUtils.getChatId(fromUser.getUser_id(), toUser.getUser_id());
        List<Chat> chats = chatService.getAllChatById(chatId);
        model.addAttribute("chats", chats);
        return "chat";
    }

}
