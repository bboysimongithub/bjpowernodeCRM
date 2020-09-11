package com.tankangya.crm.workbench.web.controller;


import com.tankangya.crm.settings.entity.User;
import com.tankangya.crm.utils.DateTimeUtil;
import com.tankangya.crm.utils.PrintJson;
import com.tankangya.crm.utils.UUIDUtil;
import com.tankangya.crm.vo.PaginationVO;
import com.tankangya.crm.workbench.entity.*;
import com.tankangya.crm.workbench.service.ActivityService;
import com.tankangya.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/clue")
public class ClueController {

    @Autowired
    private ClueService clueService;
    @Autowired
    private ActivityService activityService;
    /*@Autowired
    private Tran tran;*/

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public Object getUserList (){
        System.out.println("进入Clue控制器");
        List<User> list = clueService.getUserList();
        return list;
    }

    @RequestMapping("/getPageList.do")
    @ResponseBody
    public Object getPageList (Clue clue,
                               @RequestParam(value = "pageNo")String webPageNo,
                               @RequestParam(value = "pageSize")String webPageSize,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        System.out.println("进入pageList");

        int pageNo = Integer.valueOf(webPageNo);
        int pageSize = Integer.valueOf(webPageSize);

        int skipCount = (pageNo - 1) * pageSize;

        Map<String, Object> map = new HashMap<>();
        map.put("fullname",clue.getFullname());
        map.put("company",clue.getCompany());
        map.put("phone",clue.getPhone());
        map.put("source",clue.getSource());
        map.put("owner",clue.getOwner());
        map.put("mphone",clue.getMphone());
        map.put("state",clue.getState());
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        map.put("skipCount",skipCount);

        PaginationVO<Clue> paginationVO = clueService.getPageList(map);

        return paginationVO;
    }

    @RequestMapping("/saveClue.do")
    @ResponseBody
    public void saveClue (Clue clue,
                          HttpServletRequest request,
                          HttpServletResponse response) {

        clue.setId(UUIDUtil.getUUID());
        clue.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
        clue.setCreateTime(DateTimeUtil.getSysTime());

        boolean flag = clueService.saveClue(clue);
        PrintJson.printJsonFlag(response,flag);
    }

    @RequestMapping("/deleteClue.do")
    public void deleteClue (HttpServletRequest request,
                            HttpServletResponse response,
                            String[] id) {

        System.out.println("进入删除方法");
        //String[] param = request.getParameterValues("id");
        boolean flag = clueService.deleteClue(id);
        PrintJson.printJsonFlag(response,flag);
    }

    @RequestMapping("/editCLue.do")
    @ResponseBody
    public Object editCLue (String id) {
        System.out.println("进入edit方法");
        Map<String,Object> map = clueService.editClue(id);
        return map;
    }

    @RequestMapping("/updateClue.do")
    @ResponseBody
    public void updateClue (HttpServletRequest request,
                            HttpServletResponse response,
                            Clue clue) {

        System.out.println("进入update方法");

        clue.setEditTime(DateTimeUtil.getSysTime());
        clue.setEditBy(((User)request.getSession().getAttribute("user")).getName());

        boolean flag = clueService.updateClue(clue);
        PrintJson.printJsonFlag(response,flag);
    }

    @RequestMapping("/detail.do")
    @ResponseBody
    public ModelAndView detail(String id){
        ModelAndView mv = new ModelAndView();
        Clue clue = clueService.detail(id);
        mv.addObject("clue",clue);
        mv.setViewName("/workbench/clue/detail.jsp");
        return mv;
    }

    @RequestMapping("/getClueRemark.do")
    @ResponseBody
    public Object getClueRemark (HttpServletRequest request,
                                 HttpServletResponse response,
                                 String id) {

        System.out.println("进入查询备注信息列表");
        List<ClueRemark> list = clueService.getClueRemark(id);
        return list;
    }

    @RequestMapping("/deleteClueRemark")
    @ResponseBody
    public void deleteClueRemark (HttpServletResponse response,
                                  String id) {
        System.out.println("进入删除clue备注信息方法");
        boolean flag = clueService.deleteClueRemark(id);
        PrintJson.printJsonFlag(response,flag);
    }

    @RequestMapping("/updateClueRemark")
    @ResponseBody
    public Object updateClueRemark (HttpServletResponse response,
                                  HttpServletRequest request,
                                  ClueRemark clueRemark) {

        System.out.println("进入修改clue备注信息方法");
        clueRemark.setEditBy(((User)request.getSession().getAttribute("user")).getName());
        clueRemark.setEditTime(DateTimeUtil.getSysTime());
        clueRemark.setEditFlag("1");
        boolean flag = clueService.updateClueRemark(clueRemark);

        Map<String, Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("cr",clueRemark);
        return map;
    }

    @RequestMapping("/saveClueRemark.do")
    @ResponseBody
    public Object saveClueRemark (HttpServletResponse response,
                                HttpServletRequest request,
                                ClueRemark clueRemark) {

        System.out.println("进入添加clue备注信息方法");

        clueRemark.setId(UUIDUtil.getUUID());
        clueRemark.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
        clueRemark.setCreateTime(DateTimeUtil.getSysTime());
        clueRemark.setEditFlag("0");

        boolean flag = clueService.saveClueRemark(clueRemark);

        Map<String, Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("cr",clueRemark);

        return map;
    }

    @RequestMapping("/getActivityListByClueId.do")
    @ResponseBody
    public Object getActivityListByClueId (HttpServletRequest request,
                                           HttpServletResponse response,
                                           String clueId) {

        System.out.println("getActivityListByClueId");
        List<Activity> list = activityService.getActivityListByClueId(clueId);
        return list;
    }

    @RequestMapping("/deleteCLueActivityRelationById.do")
    @ResponseBody
    public void deleteCLueActivityRelationById (HttpServletResponse response,
                                                String id) {

        System.out.println("deleteCLueActivityRelationById");
        boolean flag = clueService.deleteCLueActivityRelationById(id);
        PrintJson.printJsonFlag(response,flag);
    }

    @RequestMapping("/getActivityListByNameAndNotClueId.do")
    @ResponseBody
    public Object getActivityListByNameAndNotClueId (HttpServletRequest request,
                                                   HttpServletResponse response,
                                                   String id, String aname) {

        System.out.println("getActivityListByNameAndNotClueId");
        List<Activity> list = activityService.getActivityListByNameAndNotClueId(id,aname);
        return list;
    }

    @RequestMapping("/saveClueActicityRaletionBttn.do")
    @ResponseBody
    public void saveClueActicityRaletionBttn (HttpServletRequest request,
                                              HttpServletResponse response,
                                              String[] id,String clueId) {

        System.out.println("saveClueActicityRaletionBttn");
        boolean flag = clueService.saveClueActicityRaletionBttn(id,clueId);
        PrintJson.printJsonFlag(response,flag);
    }

    @RequestMapping("/convert.do")
    public void saveConvert (HttpServletRequest request,
                           HttpServletResponse response,
                           String clueId,
                           String flag,
                           Tran tran) throws IOException {

        System.out.println("===========concert=========");
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        Tran t = null;
        if ("a".equals(flag)) {

            t = new Tran();

            t.setId(UUIDUtil.getUUID());
            t.setMoney(tran.getMoney());
            t.setName(tran.getName());
            t.setExpectedDate(tran.getExpectedDate());
            t.setStage(tran.getStage());
            t.setActivityId(tran.getActivityId());
            t.setCreateBy(createBy);
            t.setCreateTime(DateTimeUtil.getSysTime());
        }
        boolean flag1 = clueService.saveConvert(clueId,createBy,t);

        if(flag1){

            response.sendRedirect(request.getContextPath()+"/workbench/clue/index.jsp");

        }


    }
}
