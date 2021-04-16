package com.chl.campussecondhandtradingsystem.controller;

import com.chl.campussecondhandtradingsystem.pojo.Comment;
import com.chl.campussecondhandtradingsystem.pojo.Goods;
import com.chl.campussecondhandtradingsystem.pojo.Page;
import com.chl.campussecondhandtradingsystem.pojo.User;
import com.chl.campussecondhandtradingsystem.service.CommentService;
import com.chl.campussecondhandtradingsystem.service.GoodsService;
import com.chl.campussecondhandtradingsystem.service.UserService;
import com.chl.campussecondhandtradingsystem.utils.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GoodsController {
    private final static Logger log = LoggerFactory.getLogger(GoodsController.class);

    @Value("${trading.path.goods}")
    private String uploadPath;

    @Value("${trading.path.domain}")
    private String domain;

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

    @PostMapping("/goods/upload")
    public String uploadGoods(MultipartFile goodsImg, Goods goods, Model model, HttpSession session) {
        String originalFilename = goodsImg.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)){
            model.addAttribute("error", "文件格式不确定");
            return "addGoods";
        }
        String newFilename = MyUtils.getUUID() + suffix;
        File dest = new File(uploadPath + "/" + newFilename);
        try {
            goodsImg.transferTo(dest);
        } catch (IOException e) {
            log.error("上传文件失败： " + e.getMessage());
            throw new RuntimeException("上传文件失败");
        }
        String img = domain + "/img/goods/" + newFilename;
        goods.setImg(img);
        User u = (User) session.getAttribute("LoginUser");
        goods.setSeller(u.getUser_id());
        goodsService.uploadGoods(goods);
        return "redirect:/myGoods.html";
    }
}