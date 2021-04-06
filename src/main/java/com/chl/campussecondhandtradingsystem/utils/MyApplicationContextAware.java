package com.chl.campussecondhandtradingsystem.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
public class MyApplicationContextAware implements org.springframework.context.ApplicationContextAware {
    private static ApplicationContext APPLICATIONCONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        APPLICATIONCONTEXT = applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        return APPLICATIONCONTEXT;
    }
}
