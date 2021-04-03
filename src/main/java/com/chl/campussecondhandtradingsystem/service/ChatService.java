package com.chl.campussecondhandtradingsystem.service;

import com.chl.campussecondhandtradingsystem.dao.IChatDao;
import com.chl.campussecondhandtradingsystem.pojo.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    @Autowired
    private IChatDao chatDao;

    public List<Chat> getAllChatById(String id){
        return chatDao.getAllChatById(id);
    }
}
