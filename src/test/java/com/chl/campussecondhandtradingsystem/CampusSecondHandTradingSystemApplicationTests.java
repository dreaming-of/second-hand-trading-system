package com.chl.campussecondhandtradingsystem;

import com.chl.campussecondhandtradingsystem.Service.GoodsService;
import com.chl.campussecondhandtradingsystem.Utils.MD5Utils;
import com.chl.campussecondhandtradingsystem.pojo.Goods;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

@SpringBootTest
class CampusSecondHandTradingSystemApplicationTests {
    private final Logger log = LoggerFactory.getLogger(CampusSecondHandTradingSystemApplication.class);
    @Autowired
    private GoodsService goodsService;

    @Test
    void contextLoads() {
        log.trace("trace : test log trace");
        log.info("info : test log info");
        log.debug("debug : test log debug");
        log.warn("warn : test log warn");
        log.error("error : test log error");
    }
}
