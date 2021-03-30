package com.chl.campussecondhandtradingsystem.Controller;

import com.chl.campussecondhandtradingsystem.Service.GoodsService;
import com.chl.campussecondhandtradingsystem.Service.UserService;
import com.chl.campussecondhandtradingsystem.pojo.Goods;
import com.chl.campussecondhandtradingsystem.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;

    @GetMapping("details/{goods_id}")
    public String getGoodsDetails(@PathVariable("goods_id") int goods_id, Model model){
        Goods goods = goodsService.findGoodsById(goods_id);
        Map<String, Object> goodsDetails = new HashMap<>();
        User user = userService.findUserById(goods.getSeller());
        goodsDetails.put("goods", goods);
        goodsDetails.put("seller", user);
        model.addAttribute("goodsDetails", goodsDetails);
        return "details";
    }

    @GetMapping("findAllGoods")
    public String findAllGoods(Model model){
        return "";
    }

    @GetMapping
    public String uploadGoods(Goods goods){
        goodsService.uploadGoods(goods);
        return "";
    }
}