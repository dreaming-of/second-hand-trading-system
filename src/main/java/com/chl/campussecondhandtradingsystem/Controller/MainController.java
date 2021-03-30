package com.chl.campussecondhandtradingsystem.Controller;

import com.chl.campussecondhandtradingsystem.Service.GoodsService;
import com.chl.campussecondhandtradingsystem.Service.UserService;
import com.chl.campussecondhandtradingsystem.pojo.Goods;
import com.chl.campussecondhandtradingsystem.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping(value = {"index.html", "/", "index"})
    public String index(Model model){
        List<Goods> goodsList = goodsService.findAllGoods();
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

    @GetMapping({"setting.html", "setting"})
    public String setting(){
        return "setting";
    }


    @GetMapping("test/test.html")
    public String t(Model model){
        return "test";
    }
}
