package com.zheng.crm.settings.handler;

import com.zheng.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object obj = request.getSession().getAttribute("user");
        User user = (User) obj;
        if(user != null){
            System.out.println(user);
            return true;
        }else {
            response.sendRedirect(request.getContextPath()+"/login.jsp");
            return false;
        }

    }
}
