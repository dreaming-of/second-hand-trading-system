package com.chl.campussecondhandtradingsystem.service;

import com.chl.campussecondhandtradingsystem.dao.IOrderDao;
import com.chl.campussecondhandtradingsystem.dao.IOrderDetailsDao;
import com.chl.campussecondhandtradingsystem.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private IOrderDao orderDao;

    @Autowired
    private IOrderDetailsDao orderDetailsDao;

    public void addOrder(Order order){
        orderDao.addOrder(order);
    }
}
