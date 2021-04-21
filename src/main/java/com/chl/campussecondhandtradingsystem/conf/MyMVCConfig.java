package com.chl.campussecondhandtradingsystem.conf;

import com.chl.campussecondhandtradingsystem.interceptor.AdminInterceptor;
import com.chl.campussecondhandtradingsystem.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMVCConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/admin/**","/register", "/register.html", "/findUserByStudentNumber","/goods_img/**","/header/**","/login","/login.html", "/", "/index.html", "/index", "/css/**", "/js/**", "/img/**", "/font/**", "/layui/**");

        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login.html", "/admin/login");
    }
}
