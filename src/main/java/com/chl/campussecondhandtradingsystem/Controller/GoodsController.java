package com.chl.campussecondhandtradingsystem.Controller;

import com.chl.campussecondhandtradingsystem.Service.GoodsService;
import com.chl.campussecondhandtradingsystem.pojo.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping("findAllGoods")
    public String findAllGoods(){
        List<Goods> goodsList = goodsService.findAllGoods();
        return "";
    }

    @GetMapping
    public String uploadGoods(Goods goods){
        goodsService.uploadGoods(goods);
        return "";
    }

}
