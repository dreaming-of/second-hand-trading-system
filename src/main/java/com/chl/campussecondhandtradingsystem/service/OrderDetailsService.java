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

    public List<OrderDetails> getDetailsByGoodsIdAndBuyer(String[] ids, int buyer){
        return orderDetailsDao.getDetailsByGoodsIdAndBuyer(ids, buyer);
    }

    public void updateDetails(OrderDetails orderDetails){
        orderDetailsDao.updateOrder(orderDetails);
    }

    public void deleteShopCarDetails(int goods_id, int user_id) {
        orderDetailsDao.deleteShopCarDetails(goods_id, user_id);
    }

    public boolean findGoodsSold(int goods_id) {
        return orderDetailsDao.findGoodsSold(goods_id) != 0;
    }

    public void deleteOrderDetails(int goods_id){
        orderDetailsDao.deleteOrderDetails(goods_id);
    }

    public List<OrderDetails> getOrderDetailsByOrderId(String order_id){
        return orderDetailsDao.getOrderDetailsByOrderId(order_id);
    }
}