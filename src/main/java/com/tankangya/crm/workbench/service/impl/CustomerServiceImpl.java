package com.tankangya.crm.workbench.service.impl;


import com.tankangya.crm.settings.dao.UserDao;
import com.tankangya.crm.settings.entity.User;
import com.tankangya.crm.vo.PaginationVO;
import com.tankangya.crm.workbench.dao.CustomerDao;
import com.tankangya.crm.workbench.dao.CustomerRemarkDao;
import com.tankangya.crm.workbench.entity.Customer;
import com.tankangya.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CustomerRemarkDao customerRemarkDao;

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getUserList() {

        List<User> list = userDao.testSelectUser();
        return list;
    }

    @Override
    public boolean saveCustomer(Customer customer) {
        boolean flag = true;

        int count = customerDao.saveCustomer(customer);
        if (count != 1) {
            flag = false;
        }

        return flag;
    }

    @Override
    public PaginationVO pageCustomerList(Map<String, Object> map) {

        int total = customerDao.pageCustomerCount(map);

        List<Customer> datalist = customerDao.pageCustomerList(map);

        PaginationVO<Customer> paginationVO = new PaginationVO<>();
        paginationVO.setTotal(total);
        paginationVO.setDatalist(datalist);
        return paginationVO;
    }

    @Override
    public boolean deleteCustomerById(String[] id) {
        boolean flag = true;

        int count1 = customerRemarkDao.getCustomerRemarkById(id);
        int count2 = customerRemarkDao.deleteCustomerRemarkById(id);
        if (count1 != count2) {
            flag = false;
        }
        int count3 = customerDao.deleteCustomerById(id);
        if (count3 != id.length) {
            flag = false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> editCustomerById(String id) {
        List<User> uList = userDao.testSelectUser();
        Customer c = customerDao.editCustomerById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("uList",uList);
        map.put("c",c);
        return map;
    }

    @Override
    public boolean updateCustomerById(Customer customer) {
        boolean flag = true;

        int count = customerDao.updateCustomerById(customer);
        if (count != 1) {
            flag = false;
        }

        return flag;
    }

    @Override
    public Customer detail(String id) {

        Customer customer = customerDao.detail(id);
        return customer;
    }

}
