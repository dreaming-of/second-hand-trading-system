package com.chl.campussecondhandtradingsystem.dao;

import com.chl.campussecondhandtradingsystem.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ICommentDao {
    void addComment(Comment comment);

    List<Comment> getCommentsByGoodsId(@Param("goods_id") int goods_id, @Param("offset") int offset, @Param("limit") int limit);

    int getCommentRowsByGoodsId(int goods_id);

    List<Comment> getCommentList();
}