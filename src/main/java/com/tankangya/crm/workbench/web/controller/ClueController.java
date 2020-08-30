package com.tankangya.crm.workbench.web.controller;


import com.tankangya.crm.settings.entity.User;
import com.tankangya.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/workbench/clue")
public class ClueController {

    @Autowired
    private ClueService clueService;

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public Object getUserList (){
        System.out.println("进入Clue控制器");
        List<User> list = clueService.getUserList();
        return list;
    }
}
