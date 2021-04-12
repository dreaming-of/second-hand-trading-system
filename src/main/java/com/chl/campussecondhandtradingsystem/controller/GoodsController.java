package com.chl.campussecondhandtradingsystem.controller;

import com.chl.campussecondhandtradingsystem.pojo.Comment;
import com.chl.campussecondhandtradingsystem.pojo.Goods;
import com.chl.campussecondhandtradingsystem.pojo.Page;
import com.chl.campussecondhandtradingsystem.pojo.User;
import com.chl.campussecondhandtradingsystem.service.CommentService;
import com.chl.campussecondhandtradingsystem.service.GoodsService;
import com.chl.campussecondhandtradingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/goods/details/{goods_id}")
    public String getGoodsDetails(@PathVariable("goods_id") int goods_id, Model model, Page page) {
        Goods goods = goodsService.findGoodsById(goods_id);

        Map<String, Object> goodsDetails = new HashMap<>();
        User user = userService.findUserById(goods.getSeller());
        goodsDetails.put("goods", goods);
        goodsDetails.put("seller", user);
        model.addAttribute("goodsDetails", goodsDetails);

        page.setRows(commentService.getCommentRowsByGoodsId(goods_id));
        page.setPath("/details/" + goods_id);
        List<Comment> comments = commentService.getCommentsByGoodsId(goods_id, page.getOffset(), page.getLimit());
        List<Map<String, Object>> comsVo = new ArrayList<>();
        if (!comments.isEmpty()){
            for (Comment c : comments){
                Map<String, Object> map = new HashMap<>();
                User u = userService.findUserById(c.getUser_id());
                map.put("user", u);
                map.put("comment", c);
                comsVo.add(map);
            }
        }
        model.addAttribute("comments", comsVo);
        return "details";
    }

    @GetMapping("findAllGoods")
    public String findAllGoods(Model model) {
        return "";
    }

    @GetMapping
    public String uploadGoods(Goods goods) {
        goodsService.uploadGoods(goods);
        return "";
    }
}