package com.chl.campussecondhandtradingsystem;

import com.chl.campussecondhandtradingsystem.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CampusSecondHandTradingSystemApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        userService.updateHeader(2, "1");
    }
}