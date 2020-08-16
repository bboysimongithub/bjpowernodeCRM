package com.tankangya.crm.web.filter;

import com.tankangya.crm.settings.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //System.out.println("进来了");

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String path = httpServletRequest.getServletPath();

        if ("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)) {
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            User user = (User) httpServletRequest.getSession().getAttribute("user");

            if ( user != null ) {
                filterChain.doFilter(servletRequest,servletResponse);
            }else {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.jsp" );
            }
        }
    }

    @Override
    public void destroy() {

    }
}

