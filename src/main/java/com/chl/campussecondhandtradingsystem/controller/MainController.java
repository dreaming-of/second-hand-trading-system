package com.chl.campussecondhandtradingsystem.controller;

import com.chl.campussecondhandtradingsystem.pojo.*;
import com.chl.campussecondhandtradingsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private CommentService commentService;

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

    @GetMapping("/shopCar.html")
    public String getShopCarPage(HttpSession session, Model model){
        User u = (User) session.getAttribute("LoginUser");
        List<OrderDetails> shopCarDetails = orderDetailsService.getShopCarDetails(u.getUser_id());
        model.addAttribute("shopCarDetails", shopCarDetails);
        return "shopCar";
    }

    @GetMapping("/addGoods.html")
    public String getAddGoodsPage(){
        return "addGoods";
    }

    @GetMapping("/myGoods.html")
    public String getMyGoodsPage(HttpSession session, Model model){
        User u = (User) session.getAttribute("LoginUser");
        List<Goods> myGoodsList = goodsService.getGoodsByUserId(u.getUser_id());
        model.addAttribute("goodsList", myGoodsList);
        return "myGoods";
    }

    @GetMapping("/myOrders.html")
    public String getMyOrdersPage(Model model, HttpSession session){
        User u = (User) session.getAttribute("LoginUser");
        List<Order> orderList = orderService.getOrderByUser(u.getUser_id());
        List<Map<String, Object>> orderVo = new ArrayList<>();
        for (Order o : orderList){
            List<OrderDetails> orderDetailsList = orderDetailsService.getOrderDetailsByOrderId(o.getOrder_id());
            Map<String, Object> map = new HashMap<>();
            map.put("order", o);
            map.put("orderDetailsList", orderDetailsList);
            orderVo.add(map);
        }
        model.addAttribute("orderVoList", orderVo);
        return "myOrders";
    }

    @GetMapping("/letter.html")
    public String getLettersPage(Model model, HttpSession session){
        User u = (User) session.getAttribute("LoginUser");
        List<Chat> chatList = chatService.getChatListByUserId(u.getUser_id());
        List<User> userVo = new ArrayList<>();
        for (Chat c : chatList){
            User to;
            if (c.getFrom_id() == u.getUser_id()){
                to = userService.findUserById(c.getTo_id());
            }else{
                to = userService.findUserById(c.getFrom_id());
            }
            userVo.add(to);
        }
        model.addAttribute("userList", userVo);
        return "letter";
    }

    @GetMapping("/comment.html")
    public String getCommentPage(Model model){
        List<Comment> commentList = commentService.getCommentList();
        List<Map<String, Object>> comsVo = new ArrayList<>();
        if (!commentList.isEmpty()){
            for (Comment c : commentList){
                Map<String, Object> map = new HashMap<>();
                User u = userService.findUserById(c.getUser_id());
                map.put("user", u);
                map.put("comment", c);
                comsVo.add(map);
            }
        }
        model.addAttribute("comments", comsVo);
        return "comment";
    }

    @GetMapping("/orderpay.html/{order_id}")
    public String getOrderPayPage(Model model, @PathVariable("order_id")String id){
        Order order = orderService.findOrderById(id);
        List<OrderDetails> orderDetails = orderDetailsService.getOrderDetailsByOrderId(id);
        double total = 0;
        if (orderDetails != null){
            for (OrderDetails od : orderDetails){
                total += od.getPrice();
            }
        }
        Map<String, Object> orderVo = new HashMap<>();
        orderVo.put("order", order);
        orderVo.put("orderDetailsList", orderDetails);
        orderVo.put("total", total);
        model.addAttribute("orderVo", orderVo);
        return "orderpay";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test(HttpServletRequest request){
        return request.getRequestURL().toString();
    }
}