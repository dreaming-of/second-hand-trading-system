package com.chl.campussecondhandtradingsystem.dao;

import com.chl.campussecondhandtradingsystem.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IGoodsDao {

    List<Goods> findAllGoods();

    void uploadGoods(Goods goods);

    Goods findGoodsById(int id);

    List<Goods> getGoodsList(@Param("offset") int offset, @Param("limit") int limit);

    int getGoodsRows();

    List<Goods> getGoodsByUserId(int id);

    void deleteGoods(int goods_id);
}
