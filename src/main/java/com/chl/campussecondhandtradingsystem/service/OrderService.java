package com.chl.campussecondhandtradingsystem.service;

import com.chl.campussecondhandtradingsystem.dao.IOrderDao;
import com.chl.campussecondhandtradingsystem.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private IOrderDao orderDao;

    public void addOrder(Order order){
        orderDao.addOrder(order);
    }

    public List<Order> getOrderByUser(int user_id){
        return orderDao.getOrderByUser(user_id);
    }

    public List<Order> getOrderList() {
        return orderDao.getOrderList();
    }

    public void deleteOrderById(String order_id) {
        orderDao.deleteOrderById(order_id);
    }

    public void updateOrder(String order_id) {
        orderDao.updateOrder(order_id);
    }
}
