package com.chl.campussecondhandtradingsystem.pojo;

import java.util.Date;

public class Chat {
    private String chat_id;
    private int from_id;
    private int to_id;
    private String content;
    private Date sendTime;

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public int getTo_id() {
        return to_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "chat_id='" + chat_id + '\'' +
                ", from_id=" + from_id +
                ", to_id=" + to_id +
                ", content='" + content + '\'' +
                ", sendTime=" + sendTime +
                '}';
    }
}
