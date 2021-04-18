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

    List<OrderDetails> getDetailsByGoodsIdAndBuyer(@Param("ids") String[] id, @Param("buyer") int buyer);

    void deleteShopCarDetails(@Param("goods_id") int goods_id, @Param("buyer") int user_id);

    int findGoodsSold(int goods_id);

    void deleteOrderDetails(int goods_id);

    List<OrderDetails> getOrderDetailsByOrderId(String order_id);
}
