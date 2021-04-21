package com.chl.campussecondhandtradingsystem.controller;

import com.chl.campussecondhandtradingsystem.pojo.Goods;
import com.chl.campussecondhandtradingsystem.pojo.Order;
import com.chl.campussecondhandtradingsystem.pojo.User;
import com.chl.campussecondhandtradingsystem.service.GoodsService;
import com.chl.campussecondhandtradingsystem.service.OrderDetailsService;
import com.chl.campussecondhandtradingsystem.service.OrderService;
import com.chl.campussecondhandtradingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @GetMapping("/login.html")
    public String getAdminLoginPage(){
        return "admin/adminLogin";
    }

    @PostMapping("/login")
    public String login(User user, HttpSession session, Model model){
        User admin = userService.findAdmin(user);
        if (admin == null){
            model.addAttribute("msg", "用户名或密码错误");
            return "redirect:/admin/login.html";
        }
        session.setAttribute("admin", admin);
        return "redirect:/admin/adminUsers";
    }

    @GetMapping("/adminUsers")
    public String adminUsers(Model model){
        List<User> userList = userService.findAllUser();
        model.addAttribute("userList", userList);
        return "admin/adminUsers";
    }

    @GetMapping("/delete/user/{userId}")
    public String deleteUser(@PathVariable("userId")int user_id){
        userService.deleteUserById(user_id);
        return "redirect:/admin/adminUsers";
    }

    @GetMapping("/change/user/{userId}")
    public String getChangeUserPage(@PathVariable("userId")int user_id, Model model){
        User user = userService.findUserById(user_id);
        model.addAttribute("user", user);
        return "admin/adminChangeUsers";
    }

    @PostMapping("/change/user")
    public String changeUser(User user){
        userService.changeProfile(user);
        return "redirect:/admin/adminUsers";
    }

    @GetMapping("/upgrate/user/{userId}")
    public String upgrateUser(@PathVariable("userId")int user_id){
        userService.upgrateUserById(user_id);
        return "redirect:/admin/adminUsers";
    }

    @GetMapping("/adminGoods")
    public String adminGoods(Model model){
        List<Goods> goodsList = goodsService.findAllGoods();
        List<Map<String, Object>> goodsVo = new ArrayList<>();
        for (Goods g : goodsList){
            User user = userService.findUserById(g.getSeller());
            Map<String, Object> map = new HashMap<>();
            map.put("g", g);
            map.put("user", user);
            goodsVo.add(map);
        }
        model.addAttribute("goodsList", goodsVo);
        return "admin/adminGoods";
    }

    @GetMapping("/delete/goods/{goodsId}")
    public String deleteGoods(@PathVariable("goodsId")int goods_id){
        orderDetailsService.deleteOrderDetails(goods_id);
        goodsService.deleteGoods(goods_id);
        return "redirect:/admin/adminGoods";
    }

    @GetMapping("/adminOrders")
    public String adminOrders(Model model){
        List<Order> orderList = orderService.getOrderList();
        List<Map<String, Object>> goodsVo = new ArrayList<>();
        for (Order o : orderList){
            Map<String, Object> map = new HashMap<>();
            User buyer = userService.findUserById(o.getBuyer());
            User seller = userService.findUserById(o.getSeller());
            map.put("o", o);
            map.put("buyer", buyer);
            map.put("seller", seller);
            goodsVo.add(map);
        }
        model.addAttribute("orderList",goodsVo);
        return "admin/adminOrders";
    }

    @GetMapping("/delete/order/{orderId}")
    public String deleteOrder(@PathVariable("orderId")String order_id){
        orderService.deleteOrderById(order_id);
        return "redirect:/admin/adminOrders";
    }

    @GetMapping("/order/finish/{order_id}")
    public String finishOrder(@PathVariable("order_id")String order_id){
        orderService.updateOrder(order_id);
        return "redirect:/admin/adminOrders";
    }
}
