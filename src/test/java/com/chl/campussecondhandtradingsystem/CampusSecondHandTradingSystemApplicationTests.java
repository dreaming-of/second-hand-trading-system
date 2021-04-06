package com.chl.campussecondhandtradingsystem;

import com.chl.campussecondhandtradingsystem.pojo.Chat;
import com.chl.campussecondhandtradingsystem.service.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CampusSecondHandTradingSystemApplicationTests {

    @Autowired
    private ChatService chatService;

    @Test
    void contextLoads() {
        List<Chat> allChatById = chatService.getAllChatById("1-6");
        for (Chat c : allChatById){
            System.out.println(c);
        }
    }
}