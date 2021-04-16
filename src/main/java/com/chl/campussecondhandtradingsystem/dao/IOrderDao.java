package com.chl.campussecondhandtradingsystem.dao;

import com.chl.campussecondhandtradingsystem.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IOrderDao {
    void addOrder(Order order);

    int getOrderRows();
}
