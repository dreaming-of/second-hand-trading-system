package com.chl.campussecondhandtradingsystem.controller;

import com.chl.campussecondhandtradingsystem.pojo.Comment;
import com.chl.campussecondhandtradingsystem.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping("/comment/add")
    public String addComment(Comment comment){
        commentService.addComment(comment);
        return "redirect:/goods/details/" + comment.getGoods_id();
    }
}
