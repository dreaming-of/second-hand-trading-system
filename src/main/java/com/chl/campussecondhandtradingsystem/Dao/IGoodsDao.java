package com.chl.campussecondhandtradingsystem.Dao;

import com.chl.campussecondhandtradingsystem.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IGoodsDao {

    List<Goods> findAllGoods();

    void uploadGoods(Goods goods);
}
