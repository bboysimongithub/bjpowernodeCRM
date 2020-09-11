package com.tankangya.crm.workbench.web.controller;

import com.tankangya.crm.settings.entity.User;
import com.tankangya.crm.utils.DateTimeUtil;
import com.tankangya.crm.utils.PrintJson;
import com.tankangya.crm.utils.UUIDUtil;
import com.tankangya.crm.vo.PaginationVO;
import com.tankangya.crm.workbench.entity.Customer;
import com.tankangya.crm.workbench.service.CustomerService;
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
@RequestMapping("/workbench/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public Object getUserList () {

        System.out.println("getUserList");
        List<User> list = customerService.getUserList();
        return list;
    }

    @RequestMapping("/saveCustomer.do")
    public void saveCustomer (HttpServletRequest request,
                              HttpServletResponse response,
                              Customer customer) {
        System.out.println("saveCustomer");
        customer.setId(UUIDUtil.getUUID());
        customer.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
        customer.setCreateTime(DateTimeUtil.getSysTime());
        boolean flag = customerService.saveCustomer(customer);
        PrintJson.printJsonFlag(response,flag);
    }

    @RequestMapping("/pageCustomerList.do")
    @ResponseBody
    public Object pageCustomerList (HttpServletRequest request,
                                    HttpServletResponse response,
                                    @RequestParam("pageNo") String webPageNo,
                                    @RequestParam("pageSize") String webPageSize,
                                    Customer customer) {

        System.out.println("pageCustomerList");
        Integer pageNo = Integer.valueOf(webPageNo);
        Integer pageSize = Integer.valueOf(webPageSize);
        Integer pageCount = (pageNo - 1) * pageSize;

        Map<String, Object> map = new HashMap<>();
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        map.put("pageCount",pageCount);
        map.put("name",customer.getName());
        map.put("owner",customer.getOwner());
        map.put("phone",customer.getPhone());
        map.put("website",customer.getWebsite());

        PaginationVO<Customer> paginationVO = customerService.pageCustomerList(map);
        return paginationVO;
    }

    @RequestMapping("/deleteCustomerById.do")
    @ResponseBody
    public void deleteCustomerById (HttpServletResponse response,
                                    String[] id) {

        System.out.println("deleteCustomerById");
        boolean flag = customerService.deleteCustomerById(id);
        PrintJson.printJsonFlag(response,flag);
    }

    @RequestMapping("/editCustomerById.do")
    @ResponseBody
    public Object editCustomerById (String id) {
        System.out.println("editCustomerById");
        Map<String, Object> map = customerService.editCustomerById(id);
        return map;
    }

    @RequestMapping("/updateCustomerById.do")
    public void updateCustomerById (HttpServletRequest request,
                              HttpServletResponse response,
                              Customer customer) {
        System.out.println("updateCustomerById");
        customer.setEditBy(((User)request.getSession().getAttribute("user")).getName());
        customer.setEditTime(DateTimeUtil.getSysTime());
        boolean flag = customerService.updateCustomerById(customer);
        PrintJson.printJsonFlag(response,flag);
    }

    @RequestMapping("/detail.do")
    public ModelAndView detail (String id) {

        System.out.println("detail");
        ModelAndView mv = new ModelAndView();
        Customer customer = customerService.detail(id);

        mv.addObject("customer",customer);
        mv.setViewName("/workbench/customer/detail.jsp");
        return mv;
    }
}
