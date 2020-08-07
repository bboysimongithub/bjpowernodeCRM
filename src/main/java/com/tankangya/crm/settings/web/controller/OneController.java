package com.tankangya.crm.settings.web.controller;

import com.tankangya.crm.settings.entity.User;
import com.tankangya.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class OneController {

    @Autowired
    @Qualifier("myService")
    private UserService userService;

    @RequestMapping("/test.do")
    @ResponseBody
    public List<User> selectUser () {
        List<User> list = userService.queryUser();
        return list;

    }
}
