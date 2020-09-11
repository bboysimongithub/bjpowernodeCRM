package com.tankangya.crm.workbench.service;

import com.tankangya.crm.settings.entity.User;
import com.tankangya.crm.vo.PaginationVO;
import com.tankangya.crm.workbench.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface CustomerService {

    List<User> getUserList();

    boolean saveCustomer(Customer customer);

    PaginationVO<Customer> pageCustomerList(Map<String, Object> map);

    boolean deleteCustomerById(String[] id);

    Map<String, Object> editCustomerById(String id);

    boolean updateCustomerById(Customer customer);

    Customer detail(String id);
}
