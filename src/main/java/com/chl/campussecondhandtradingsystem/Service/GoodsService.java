package com.chl.campussecondhandtradingsystem.Service;

import com.chl.campussecondhandtradingsystem.Dao.IGoodsDao;
import com.chl.campussecondhandtradingsystem.pojo.Goods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {
    private final Logger log = LoggerFactory.getLogger(GoodsService.class);

    @Autowired
    private IGoodsDao goodsDao;

    public List<Goods> findAllGoods() {
        return goodsDao.findAllGoods();
    }

    public void uploadGoods(Goods goods) {
        goodsDao.uploadGoods(goods);
        log.info("");
    }
}