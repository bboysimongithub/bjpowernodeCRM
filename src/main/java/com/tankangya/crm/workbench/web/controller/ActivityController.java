package com.tankangya.crm.workbench.web.controller;

import com.tankangya.crm.settings.entity.User;
import com.tankangya.crm.settings.service.UserService;
import com.tankangya.crm.utils.DateTimeUtil;
import com.tankangya.crm.utils.PrintJson;
import com.tankangya.crm.utils.UUIDUtil;
import com.tankangya.crm.vo.PaginationVO;
import com.tankangya.crm.workbench.entity.Activity;
import com.tankangya.crm.workbench.entity.ActivityRemark;
import com.tankangya.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

        System.out.println("保存");
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

        boolean flag = activityService.saveActivity(activity);


        PrintJson.printJsonFlag(response,flag);
    }

    @RequestMapping(value = "/pageList.do", method = RequestMethod.GET)
    @ResponseBody
    public Object pageList (HttpServletRequest request,
                          HttpServletResponse response,
                          Activity activity,
                          @RequestParam(value = "pageNo") String webPageNo,
                          @RequestParam(value = "pageSize") String webPageSize){

        System.out.println("进入pageList方法111");

        int pageNo = Integer.valueOf(webPageNo);
        int pageSize = Integer.valueOf(webPageSize);

        int skipCount = (pageNo-1)*pageSize;
        Map<String, Object> map = new HashMap<>();
        map.put("name",activity.getName());
        map.put("owner",activity.getOwner());
        map.put("startDate",activity.getStartDate());
        map.put("endDate",activity.getEndDate());
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        map.put("skipCount", skipCount);

        PaginationVO<Activity> paginationVO = activityService.pageList(map);

        return paginationVO;
    }

    @RequestMapping(value = "/delete.do")
    @ResponseBody
    public void delete (HttpServletRequest request,
                        HttpServletResponse response,
                        String[] id) {

        System.out.println("执行市场活动的删除操作");

        String ids[] = request.getParameterValues("id");


        boolean flag = activityService.deleteActivity(ids);

        PrintJson.printJsonFlag(response, flag);
    }

    @RequestMapping(value = "/getUserAndActivity.do", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserAndActivity (HttpServletRequest request,
                                    HttpServletResponse response,
                                    String id) {

        System.out.println("getUserAndActivity方法");
        Map<String, Object> map = activityService.getUserAndActivity(id);

        return map;

    }

    @RequestMapping(value = "/update.do",method = RequestMethod.POST)
    @ResponseBody
    public void update (HttpServletRequest request,
                        HttpServletResponse response,
                        Activity activity) {

        activity.setEditTime(DateTimeUtil.getSysTime());
        activity.setEditBy(((User)request.getSession().getAttribute("user")).getName());


        boolean flag = activityService.updateActivity (activity);


        PrintJson.printJsonFlag(response,flag);
    }

    @RequestMapping(value = "/detail.do")
    @ResponseBody
    public ModelAndView detail (String id) {

        ModelAndView mv = new ModelAndView();
        Activity activity = activityService.detail(id);
        mv.addObject("activity",activity);
        mv.setViewName("/workbench/activity/detail.jsp");
        return mv;
    }
    @RequestMapping(value = "/getRemarkById.do")
    @ResponseBody
    public Object getRemarkById (HttpServletRequest request) {

        System.out.println("进入查询备注信息");
        String activityId1 = request.getParameter("activityId");
        List<ActivityRemark> list = activityService.getRemarkById(activityId1);
        return list;
    }

    @RequestMapping("/deleteRemark.do")
    @ResponseBody
    public void deleteRemark (HttpServletResponse response, String id) {

        System.out.println("进入备注表删除方法");
        boolean flag = activityService.deleteRemark(id);

        PrintJson.printJsonFlag(response,flag);

    }

    @RequestMapping("/saveRemark.do")
    @ResponseBody
    public Object saveRemark (HttpServletRequest request,
                              HttpServletResponse response,
                              ActivityRemark activityRemark) {
        System.out.println("进入添加备注信息控制器");

        activityRemark.setId(UUIDUtil.getUUID());
        activityRemark.setCreateTime(DateTimeUtil.getSysTime());
        activityRemark.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
        activityRemark.setEditFlag("0");

        boolean flag = activityService.saveRemark(activityRemark);

        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("ar",activityRemark);
        return map;
    }

    @RequestMapping("/updateRemark.do")
    @ResponseBody
    public Object updateRemark (HttpServletRequest request,
                              HttpServletResponse response,
                              ActivityRemark activityRemark) {

        System.out.println("进入修改备注信息控制器");

        activityRemark.setEditTime(DateTimeUtil.getSysTime());
        activityRemark.setEditBy(((User)request.getSession().getAttribute("user")).getName());
        activityRemark.setEditFlag("1");

        boolean flag = activityService.updateRemark(activityRemark);

        Map<String, Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("ar",activityRemark);

        return map;
    }
}
