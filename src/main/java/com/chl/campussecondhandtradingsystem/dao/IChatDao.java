package com.chl.campussecondhandtradingsystem.dao;

import com.chl.campussecondhandtradingsystem.pojo.Chat;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IChatDao {
    List<Chat> getAllChatById(String id);

    int saveChat(Chat chat);

    List<Chat> getChatListByUserId(int id);
}
