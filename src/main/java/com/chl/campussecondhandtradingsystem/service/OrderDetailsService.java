package com.chl.campussecondhandtradingsystem.service;

import com.chl.campussecondhandtradingsystem.dao.IOrderDetailsDao;
import com.chl.campussecondhandtradingsystem.pojo.OrderDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsService {
    @Autowired
    private IOrderDetailsDao orderDetailsDao;

    public void addOrderDetails(OrderDetails orderDetails){
        orderDetailsDao.insertDetails(orderDetails);
    }

    public List<OrderDetails> getShopCarDetails(int id){
        return orderDetailsDao.getShopCarDetails(id);
    }

    public List<OrderDetails> getDetailsByGoodsIdAndSeller(String[] ids, int seller){
        return orderDetailsDao.getDetailsByGoodsIdAndSeller(ids, seller);
    }

    public void updateDetails(OrderDetails orderDetails){
        orderDetailsDao.updateOrder(orderDetails);
    }

}