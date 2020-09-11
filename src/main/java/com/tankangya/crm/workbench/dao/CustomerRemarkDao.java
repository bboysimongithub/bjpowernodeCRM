package com.tankangya.crm.workbench.dao;

import com.tankangya.crm.workbench.entity.CustomerRemark;

public interface CustomerRemarkDao {

    int saveCustomerRemark(CustomerRemark customerRemark);

    int getCustomerRemarkById(String[] id);

    int deleteCustomerRemarkById(String[] id);
}
