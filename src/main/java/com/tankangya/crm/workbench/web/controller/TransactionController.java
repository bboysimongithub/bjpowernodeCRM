package com.tankangya.crm.workbench.web.controller;

import com.tankangya.crm.settings.entity.User;
import com.tankangya.crm.settings.service.UserService;
import com.tankangya.crm.utils.DateTimeUtil;
import com.tankangya.crm.utils.UUIDUtil;
import com.tankangya.crm.vo.PaginationVO;
import com.tankangya.crm.workbench.entity.Activity;
import com.tankangya.crm.workbench.entity.Contacts;
import com.tankangya.crm.workbench.entity.Tran;
import com.tankangya.crm.workbench.entity.TranHistory;
import com.tankangya.crm.workbench.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;

    @RequestMapping("/save.do")
    @ResponseBody
    public ModelAndView getUserList () {
        ModelAndView mv = new ModelAndView();
        List<User> list = userService.queryUser();

        mv.addObject("list",list);
        mv.setViewName("/workbench/transaction/save.jsp");

        return mv;
    }

    @RequestMapping("/getCustomerName.do")
    @ResponseBody
    public Object getCustomerName (String name) {

        System.out.println("getCustomerName");
        List<String> list = transactionService.getCustomerName(name);
        return list;
    }

    @RequestMapping("/getActivityList.do")
    @ResponseBody
    public Object getActivityList (String name) {

        System.out.println("getActivityList");
        List<Activity> list = transactionService.getActivityList(name);
        return list;
    }

    @RequestMapping("/getContactsList.do")
    @ResponseBody
    public Object getContactsList (String fullname) {

        System.out.println("getContactsList");
        List<Contacts> list = transactionService.getContactsList(fullname);
        return list;
    }

    @RequestMapping("/saveTran.do")
    public String saveTran (Tran tran,
                            HttpServletRequest request,
                            HttpServletResponse response,
                            String customerName) {

        System.out.println("saveTran");
        tran.setId(UUIDUtil.getUUID());
        tran.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
        tran.setCreateTime(DateTimeUtil.getSysTime());

        boolean flag = transactionService.saveTran(tran,customerName);

        if (flag) {
            return "/workbench/transaction/index.jsp";
        }else {
            return "/workbench/transaction/index.jsp";
        }
    }

    @RequestMapping("/pageTransactionList.do")
    @ResponseBody
    public Object pageTransactionList (HttpServletRequest request,
                                       HttpServletResponse response,
                                       @RequestParam("pageNo")String webPageNo,
                                       @RequestParam("pageSize")String webPageSize,
                                       Tran tran) {

        System.out.println("pageTransactionList");
        int pageNo = Integer.valueOf(webPageNo);
        int pageSize = Integer.valueOf(webPageSize);
        int countPage = (pageNo - 1) * pageSize;

        Map<String, Object> map = new HashMap<>();
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        map.put("countPage",countPage);
        map.put("owner",tran.getOwner());
        map.put("name",tran.getName());
        map.put("customerId",tran.getCustomerId());
        map.put("stage",tran.getStage());
        map.put("type",tran.getType());
        map.put("source",tran.getSource());
        map.put("contactsId",tran.getContactsId());

        PaginationVO paginationVO = transactionService.pageTransactionList(map);
        return paginationVO;
    }

    @RequestMapping("/detail.do")
    public ModelAndView detail(HttpServletRequest request,
                               String id) {
        ModelAndView mv = new ModelAndView();
        Tran tran = transactionService.detail(id);

        Map<String, String> map = (Map<String, String>) request.getServletContext().getAttribute("pMap");
        String stage = tran.getStage();
        String possibility = map.get(stage);
        tran.setPossibility(possibility);
        mv.addObject("t",tran);
        mv.setViewName("/workbench/transaction/detail.jsp");
        return mv;
    }

    @RequestMapping("/getHistoryByTranId.do")
    @ResponseBody
    public Object getHistoryByTranId (String tranId,
                                      HttpServletRequest request) {
        System.out.println("getHistoryByTranId");
        List<TranHistory> list = transactionService.getHistoryByTranId(tranId);
        Map<String, String> map = (Map<String, String>) request.getServletContext().getAttribute("pMap");
        for (TranHistory th:list) {
            String stage = th.getStage();
            String possibility = map.get(stage);
            th.setPossibility(possibility);
        }
        return list;
    }

    @RequestMapping("/saveChangeStage.do")
    @ResponseBody
    public Object saveChangeStage (Tran tran,
                                   HttpServletRequest request) {
        System.out.println("saveChangeStage");
        boolean flag = transactionService.saveChangeStage(tran);
        Map<String, String> map = (Map<String, String>) request.getServletContext().getAttribute("pMap");
        String possibility = map.get(tran.getStage());
        tran.setPossibility(possibility);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("success",flag);
        map1.put("t",tran);
        return map1;
    }
}
