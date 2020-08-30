package com.tankangya.crm.settings.web.controller;

import com.tankangya.crm.settings.entity.DicValue;
import com.tankangya.crm.settings.entity.User;
import com.tankangya.crm.settings.service.DicService;
import com.tankangya.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class OneController {

    @Autowired
    @Qualifier("myService")
    private UserService userService;
    @Autowired
    private DicService dicService;

    @RequestMapping("/test.do")
    @ResponseBody
    public Object selectUser (HttpServletRequest request,
                              HttpServletResponse response) {
        System.out.println("创建上下文作用域监听器");

        ServletContext application = request.getServletContext();

        Map<String, List<DicValue>> map = dicService.getAll();

        Set<String> set = map.keySet();

        for (String key: set) {
            application.setAttribute(key,map.get(key));
        }
        return map;
    }
}
