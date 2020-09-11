package com.tankangya.crm.workbench.dao;

import com.tankangya.crm.workbench.entity.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerDao {

    Customer getCustomerByCompany(String name);

    int saveCustomer(Customer customer);

    int pageCustomerCount(Map<String, Object> map);

    List<Customer> pageCustomerList(Map<String, Object> map);

    int deleteCustomerById(String[] id);

    Customer editCustomerById(String id);

    int updateCustomerById(Customer customer);

    Customer detail(String id);

    List<String> getCustomerName(String name);
}
