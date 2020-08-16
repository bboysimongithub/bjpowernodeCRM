package com.tankangya.crm.web.handler;

import com.tankangya.crm.settings.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("进来啦啦");

        String path = request.getServletPath();

        if ("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)) {

            return true;

        }else {

            User user = (User) request.getSession().getAttribute("user");

            if (user != null) {

                return true;

            }else {

                response.sendRedirect( request.getContextPath() + "/login.jsp");

                return false;
            }
        }
    }
}
