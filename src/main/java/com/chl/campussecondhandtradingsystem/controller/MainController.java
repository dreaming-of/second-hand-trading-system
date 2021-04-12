package com.chl.campussecondhandtradingsystem.controller;

import com.chl.campussecondhandtradingsystem.pojo.Goods;
import com.chl.campussecondhandtradingsystem.pojo.Page;
import com.chl.campussecondhandtradingsystem.pojo.User;
import com.chl.campussecondhandtradingsystem.service.GoodsService;
import com.chl.campussecondhandtradingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/index.html", "/", "/index"})
    public String index(Model model, Page page){
        page.setPath("/index");
        page.setRows(goodsService.getGoodsRows());
        page.setLimit(12);
        List<Goods> goodsList = goodsService.getGoodsList(page.getOffset(), page.getLimit());
        List<Map<String, Object>> goodsVoList = new ArrayList<>();
        for (Goods g : goodsList) {
            User user = userService.findUserById(g.getSeller());
            Map<String, Object> map = new HashMap<>();
            map.put("goods", g);
            map.put("seller", user);
            goodsVoList.add(map);
        }
        model.addAttribute("goodsList", goodsVoList);
        return "index";
    }

    @GetMapping("/login.html")
    public String getLoginPage(HttpSession session){
        if(session.getAttribute("LoginUser") != null)
            return "index";
        return "login";
    }

    @GetMapping("/register.html")
    public String getRegisterPage(){
        return "register";
    }

    @GetMapping({"/setting.html", "/setting"})
    public String getSettingPage(){
        return "setting";
    }

    @GetMapping("/test")
    @ResponseBody
    public List t2(){
        return userService.findAllUser();
    }

}
