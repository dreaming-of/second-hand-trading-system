package com.chl.campussecondhandtradingsystem.dao;

import com.chl.campussecondhandtradingsystem.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IOrderDao {
    void addOrder(Order order);

    List<Order> getOrderByUser(int id);

    List<Order> getOrderList();

    void deleteOrderById(String order_id);

    void updateOrder(@Param("order_id") String order_id, @Param("status")int status);

    Order findOrderById(String order_id);
}
