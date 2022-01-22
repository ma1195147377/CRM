package com.zheng.crm.settings.web.filter;


import com.zheng.crm.settings.domain.User;

import javax.jws.soap.SOAPBinding;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();

        if ("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)) {
            chain.doFilter(servletRequest, servletResponse);
        } else {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user != null) {
                chain.doFilter(servletRequest, servletResponse);
            } else {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
