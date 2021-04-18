package com.chl.campussecondhandtradingsystem.controller;

import com.chl.campussecondhandtradingsystem.pojo.Goods;
import com.chl.campussecondhandtradingsystem.pojo.OrderDetails;
import com.chl.campussecondhandtradingsystem.pojo.User;
import com.chl.campussecondhandtradingsystem.service.GoodsService;
import com.chl.campussecondhandtradingsystem.service.OrderDetailsService;
import com.chl.campussecondhandtradingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderDetailsController {
    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;

    @GetMapping("/addShopCar/{goods_id}")
    public String addShopCar(@PathVariable("goods_id") int id, HttpSession session){
        Goods goods = goodsService.findGoodsById(id);
        User buyer = (User) session.getAttribute("LoginUser");
        if (goods.getSeller() == buyer.getUser_id()){
            return "redirect:/index";
        }
        String [] ids = new String [1];
        ids[0] = String.valueOf(id);
        List<OrderDetails> list = orderDetailsService.getDetailsByGoodsIdAndBuyer(ids, buyer.getUser_id());
        if (list.isEmpty()){
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setGoods_id(goods.getGoods_id());
            orderDetails.setGoods_name(goods.getGoods_name());
            orderDetails.setPrice(goods.getPrice());
            orderDetails.setBuyer(buyer.getUser_id());
            orderDetails.setGoods_img(goods.getImg());

            User seller = userService.findUserById(goods.getSeller());
            orderDetails.setSeller(seller.getUser_id());
            orderDetails.setStatus(0);
            orderDetailsService.addOrderDetails(orderDetails);
        }
        return "redirect:/index";
    }

    @GetMapping("/delete/shopcar/{goods_id}")
    public String deleteShopCarItem(@PathVariable("goods_id")int goods_id, HttpSession session){
        User u = (User) session.getAttribute("LoginUser");
        orderDetailsService.deleteShopCarDetails(goods_id, u.getUser_id());
        return "redirect:/shopCar.html";
    }
}
