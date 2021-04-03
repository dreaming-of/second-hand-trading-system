package com.chl.campussecondhandtradingsystem.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chl.campussecondhandtradingsystem.dao.IChatDao;
import com.chl.campussecondhandtradingsystem.pojo.Chat;
import com.chl.campussecondhandtradingsystem.pojo.User;
import com.chl.campussecondhandtradingsystem.utils.MyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket", configurator = GetHttpSessionConfig.class)
@Component
public class ChatEndPoint {
    private final static Logger log = LoggerFactory.getLogger(ChatEndPoint.class);

    private static Map<Integer, ChatEndPoint> map = new ConcurrentHashMap<>();

    private Session session;

    private HttpSession httpSession;

    @Autowired
    private IChatDao chatDao;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        this.session = session;
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
        User user = (User) httpSession.getAttribute("LoginUser");
        map.put(user.getUser_id(), this);
    }

    @OnMessage
    public void onMessage(String message){
        JSONObject jsonObject = JSON.parseObject(message);
        Integer toUserId = (Integer) jsonObject.get("toUser");
        ChatEndPoint toUser = map.get(toUserId);
        Chat chat = new Chat();
        User fromUser = (User) httpSession.getAttribute("LoginUser");
        chat.setChatId(MyUtils.getChatId(fromUser.getUser_id(), toUserId));
        String content = jsonObject.getString("content");
        chat.setContent(content);
        chat.setToId(toUserId);
        chat.setFromId(fromUser.getUser_id());
        chat.setSendTime(new Date());
        if (toUser != null){
            try {
                toUser.session.getBasicRemote().sendText(content);
            } catch (IOException e) {
                log.error("发送信息失败：" + e.getMessage());
            }
        }
        chatDao.saveChat(chat);
    }

    @OnClose
    public void onClose(){
        map.remove(((User)httpSession.getAttribute("LoginUser")).getUser_id());
    }
}