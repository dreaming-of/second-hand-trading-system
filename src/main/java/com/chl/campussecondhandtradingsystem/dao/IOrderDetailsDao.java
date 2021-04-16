package com.chl.campussecondhandtradingsystem.dao;

import com.chl.campussecondhandtradingsystem.pojo.OrderDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IOrderDetailsDao {
    void insertDetails(OrderDetails orderDetails);

    void updateOrder(OrderDetails orderDetails);

    List<OrderDetails> getShopCarDetails(int id);

    List<OrderDetails> getDetailsByGoodsIdAndSeller(@Param("ids") String[] id, @Param("buyer") int buyer);
}
