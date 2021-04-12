package com.chl.campussecondhandtradingsystem.service;

import com.chl.campussecondhandtradingsystem.dao.ICommentDao;
import com.chl.campussecondhandtradingsystem.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private ICommentDao commentDao;

    public void addComment(Comment comment){
        comment.setCreate_time(new Date());
        commentDao.addComment(comment);
    }

    public List<Comment> getCommentsByGoodsId(int goods_id, int offset, int limit){
        return commentDao.getCommentsByGoodsId(goods_id, offset, limit);
    }

    public int getCommentRowsByGoodsId(int goods_id){
        return commentDao.getCommentRowsByGoodsId(goods_id);
    }
}
