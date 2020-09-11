package com.tankangya.crm.workbench.service.impl;

import com.tankangya.crm.utils.DateTimeUtil;
import com.tankangya.crm.utils.UUIDUtil;
import com.tankangya.crm.vo.PaginationVO;
import com.tankangya.crm.workbench.dao.*;
import com.tankangya.crm.workbench.entity.*;
import com.tankangya.crm.workbench.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private ContactsDao contactsDao;

    @Override
    public List<String> getCustomerName(String name) {

        List<String> list = customerDao.getCustomerName(name);
        return list;
    }

    @Override
    public List<Activity> getActivityList(String name) {

        List<Activity> list = activityDao.getActivityList(name);
        return list;
    }

    @Override
    public List<Contacts> getContactsList(String fullname) {

        List<Contacts> list = contactsDao.getContactsList(fullname);
        return list;
    }

    @Override
    public boolean saveTran(Tran tran, String customerName) {
        boolean flag = true;

        Customer customer = customerDao.getCustomerByCompany(customerName);
        if (customer == null) {
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(tran.getOwner());
            customer.setName(customerName);
            customer.setCreateBy(tran.getCreateBy());
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setNextContactTime(tran.getNextContactTime());
            customer.setContactSummary(tran.getContactSummary());
            customer.setDescription(tran.getDescription());

            int count1 = customerDao.saveCustomer(customer);
            if (count1 != 1) {
                flag = false;
            }
        }

        tran.setCustomerId(customer.getId());

        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setStage(tran.getStage());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setCreateBy(tran.getCreateBy());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setTranId(tran.getId());

        int count2 = tranHistoryDao.savetranHistory(tranHistory);
        if (count2 != 1) {
            flag = false;
        }

        int count3 = tranDao.saveTran(tran);
        if (count3 != 1) {
            flag = false;
        }

        return flag;
    }

    @Override
    public PaginationVO pageTransactionList(Map<String, Object> map) {

        int total = tranDao.getTranCount(map);

        List datalist = tranDao.getTranList(map);

        PaginationVO paginationVO = new PaginationVO();
        paginationVO.setTotal(total);
        paginationVO.setDatalist(datalist);

        return paginationVO;
    }

    @Override
    public Tran detail(String id) {

        Tran tran = tranDao.detail(id);
        return tran;
    }

    @Override
    public List<TranHistory> getHistoryByTranId(String tranId) {

        List<TranHistory> list = tranHistoryDao.getHistoryByTranId(tranId);
        return list;
    }
}
