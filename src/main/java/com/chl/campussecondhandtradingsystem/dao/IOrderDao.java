package com.chl.campussecondhandtradingsystem.dao;

import com.chl.campussecondhandtradingsystem.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IOrderDao {
    void addOrder(Order order);

    List<Order> getOrderByUser(int id);

    List<Order> getOrderList();

    void deleteOrderById(String order_id);

    void updateOrder(String order_id);
}
