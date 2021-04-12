package com.chl.campussecondhandtradingsystem.controller;

import com.chl.campussecondhandtradingsystem.pojo.Chat;
import com.chl.campussecondhandtradingsystem.pojo.User;
import com.chl.campussecondhandtradingsystem.service.ChatService;
import com.chl.campussecondhandtradingsystem.service.UserService;
import com.chl.campussecondhandtradingsystem.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @GetMapping("/chat/{id}")
    public String chat(@PathVariable("id")int toUserId, Model model, HttpSession session){
        User fromUser = (User) session.getAttribute("LoginUser");
        User toUser = userService.findUserById(toUserId);
        String chatId = MyUtils.getChatId(fromUser.getUser_id(), toUserId);
        List<Chat> contents = chatService.getAllChatById(chatId);
        Chat last = new Chat();
        if (!contents.isEmpty()){
            last = contents.get(contents.size() - 1);
        }
        model.addAttribute("contents", contents);
        model.addAttribute("toUser", toUser);
        model.addAttribute("last",last);
        return "chat";
    }


}
