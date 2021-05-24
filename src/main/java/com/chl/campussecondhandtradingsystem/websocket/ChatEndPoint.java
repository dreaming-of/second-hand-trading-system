package com.chl.campussecondhandtradingsystem.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chl.campussecondhandtradingsystem.pojo.Chat;
import com.chl.campussecondhandtradingsystem.pojo.User;
import com.chl.campussecondhandtradingsystem.service.ChatService;
import com.chl.campussecondhandtradingsystem.service.UserService;
import com.chl.campussecondhandtradingsystem.utils.MyApplicationContextAware;
import com.chl.campussecondhandtradingsystem.utils.MyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket/{userId}")
@Component
public class ChatEndPoint {
    private final static Logger log = LoggerFactory.getLogger(ChatEndPoint.class);

    private static Map<Integer, ChatEndPoint> map = new ConcurrentHashMap<>();

    private Session session;

    private ChatService chatService = MyApplicationContextAware.getApplicationContext().getBean(ChatService.class);

    private UserService userService = MyApplicationContextAware.getApplicationContext().getBean(UserService.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") int userId, EndpointConfig config) {
        this.session = session;
        User user = userService.findUserById(userId);
        map.put(user.getUser_id(), this);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("userId") int userId) {
        JSONObject jsonObject = JSON.parseObject(message);
        int toUserId = Integer.parseInt((String) jsonObject.get("toUser"));
        ChatEndPoint toUser = map.get(toUserId);
        Chat chat = new Chat();
        chat.setChat_id(MyUtils.getChatId(userId, toUserId));
        String content = jsonObject.getString("content");
        chat.setContent(content);
        chat.setTo_id(toUserId);
        chat.setFrom_id(userId);
        chat.setSendTime(new Date());
        if (toUser != null) {
            try {
                toUser.session.getBasicRemote().sendText(content);
            } catch (IOException e) {
                log.error("发送信息失败：" + e.getMessage());
            }
        }
        chatService.saveChat(chat);
    }

    @OnClose
    public void onClose(@PathParam("userId") int userId) {
        map.remove(userId);
    }
}