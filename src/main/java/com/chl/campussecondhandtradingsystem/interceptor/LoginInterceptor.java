package com.chl.campussecondhandtradingsystem.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object loginUser = session.getAttribute("LoginUser");
        if(loginUser != null){
            return true;
        }
        request.setAttribute("msg", "请先登录");
        request.getRequestDispatcher("/login.html").forward(request, response);
        return false;
    }
}