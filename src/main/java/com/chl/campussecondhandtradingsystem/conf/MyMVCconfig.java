package com.chl.campussecondhandtradingsystem.conf;

import com.chl.campussecondhandtradingsystem.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMVCconfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/register", "/register.html", "/login","/login.html", "/", "index.html", "index", "/css/**", "/js/**", "/img/**", "/font/**", "/layui/**");
    }
}
