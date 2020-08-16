package com.tankangya.crm.workbench.web.controller;

import com.tankangya.crm.settings.entity.User;
import com.tankangya.crm.settings.service.UserService;
import com.tankangya.crm.utils.DateTimeUtil;
import com.tankangya.crm.utils.PrintJson;
import com.tankangya.crm.utils.ServiceFactory;
import com.tankangya.crm.utils.UUIDUtil;
import com.tankangya.crm.workbench.entity.Activity;
import com.tankangya.crm.workbench.service.ActivityService;
import com.tankangya.crm.workbench.service.impl.ActivityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {

    @Autowired
    @Qualifier("workbenchService")
    private ActivityService activityService;

    @Autowired
    private UserService userService;


    @RequestMapping("/getUserList.do")
    @ResponseBody
    public Object add () {
       List<User> list= userService.queryUser();
       return list;
    }

    @RequestMapping(value = "/save.do",method = RequestMethod.POST)
    @ResponseBody
    public void save (HttpServletRequest request, HttpServletResponse response, Activity activity) {

        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateTimeUtil.getSysTime());
        activity.setCreateBy(((User)request.getSession().getAttribute("user")).getName());

        /*String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        Activity a = new Activity();
        a.setId(id);
        a.setOwner(owner);
        a.setName(name);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setCost(cost);
        a.setDescription(description);
        a.setCreateTime(createTime);
        a.setCreateBy(createBy);*/

        boolean flag = activityService.save(activity);

        PrintJson.printJsonFlag(response,flag);
    }
}
