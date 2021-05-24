package com.chl.campussecondhandtradingsystem.controller;

import com.chl.campussecondhandtradingsystem.pojo.Order;
import com.chl.campussecondhandtradingsystem.pojo.OrderDetails;
import com.chl.campussecondhandtradingsystem.pojo.User;
import com.chl.campussecondhandtradingsystem.service.OrderDetailsService;
import com.chl.campussecondhandtradingsystem.service.OrderService;
import com.chl.campussecondhandtradingsystem.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @PostMapping("/buy")
    public String generateNewOrder(@RequestParam("goods") String[] goodsId, HttpSession session) {
        User u = (User) session.getAttribute("LoginUser");
        List<OrderDetails> selectedGoods = orderDetailsService.getDetailsByGoodsIdAndBuyer(goodsId, u.getUser_id());
        Map<Integer, String> map = new HashMap<>();
        for (OrderDetails o : selectedGoods) {
            int seller = o.getSeller();
            if (!map.containsKey(seller)) {
                map.put(seller, MyUtils.getOrderId());
            }
            o.setOrder_id(map.get(seller));
            o.setStatus(1);
            orderDetailsService.updateDetails(o);
        }
        for (Integer i : map.keySet()){
            String order_id = map.get(i);
            Order order = new Order();
            order.setBuyer(u.getUser_id());
            order.setSeller(i);
            order.setCreate_time(new Date());
            order.setOrder_id(order_id);
            order.setStatus(0);
            orderService.addOrder(order);
        }
        return "redirect:/myOrders.html";
    }

    @GetMapping("/order/update/{order_id}/{status}")
    public String finishOrder(@PathVariable("order_id")String order_id, @PathVariable("status")int status){
        orderService.updateOrder(order_id, status);
        return "redirect:/myOrders.html";
    }
}
