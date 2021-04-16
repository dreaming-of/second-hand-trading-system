package com.chl.campussecondhandtradingsystem;

import com.chl.campussecondhandtradingsystem.utils.MyUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CampusSecondHandTradingSystemApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(MyUtils.getOrderId());
    }
}