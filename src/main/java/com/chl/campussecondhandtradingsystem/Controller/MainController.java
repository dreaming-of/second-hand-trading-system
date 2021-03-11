package com.chl.campussecondhandtradingsystem.Controller;

import com.alibaba.fastjson.JSON;
import com.chl.campussecondhandtradingsystem.Service.GoodsService;
import com.chl.campussecondhandtradingsystem.pojo.Goods;
import com.chl.campussecondhandtradingsystem.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping(value = {"index.html", "/", "index"})
    public String index(Model model){
        List<Goods> goodsList = goodsService.findAllGoods();
        model.addAttribute("goodsList", goodsList);
        return "index";
    }

    @GetMapping(value = "login.html")
    public String login(HttpSession session){
        if(session.getAttribute("LoginUser") != null)
            return "index";
        return "login";
    }

    @GetMapping("register.html")
    public String register(){
        return "register";
    }


    @GetMapping("/test.html")
    public String t(){
        return "test";
    }
}
