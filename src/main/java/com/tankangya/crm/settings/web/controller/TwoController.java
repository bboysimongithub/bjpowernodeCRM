package com.tankangya.crm.settings.web.controller;

import com.tankangya.crm.settings.entity.User;
import com.tankangya.crm.settings.service.UserService;
import com.tankangya.crm.settings.service.impl.UserServiceImpl;
import com.tankangya.crm.utils.MD5Util;
import com.tankangya.crm.utils.PrintJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/settings/user")
public class TwoController {

    @Autowired
    @Qualifier("myService")
    private UserService service;

    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    public void login (HttpServletRequest request,
                               HttpServletResponse response,
                               String loginAct, String loginPwd) {

        System.out.println("访问后台成功");

        loginPwd = MD5Util.getMD5(loginPwd);

        String ip = request.getRemoteAddr();


        try {

            User user = service.login (loginAct,loginPwd,ip);

            request.getSession().setAttribute("user",user);

            PrintJson.printJsonFlag(response,true);
        }catch (Exception e) {

            e.printStackTrace();

            String msg = e.getMessage();

            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("msg",msg);

            PrintJson.printJsonObj(response,map);
        }
    }
}
