package com.chl.campussecondhandtradingsystem.controller;

import com.chl.campussecondhandtradingsystem.pojo.*;
import com.chl.campussecondhandtradingsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

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

    @PostMapping("/test")
    @ResponseBody
    public List t2(@RequestParam("goods") String[] goodsId){
        return Arrays.asList(goodsId);
    }

}
