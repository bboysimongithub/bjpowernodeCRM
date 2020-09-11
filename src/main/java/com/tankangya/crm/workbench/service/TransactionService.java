package com.tankangya.crm.workbench.service;

import com.tankangya.crm.vo.PaginationVO;
import com.tankangya.crm.workbench.entity.Activity;
import com.tankangya.crm.workbench.entity.Contacts;
import com.tankangya.crm.workbench.entity.Tran;
import com.tankangya.crm.workbench.entity.TranHistory;

import java.util.List;
import java.util.Map;

public interface TransactionService {

    List<String> getCustomerName(String name);

    List<Activity> getActivityList(String name);

    List<Contacts> getContactsList(String fullname);

    boolean saveTran(Tran tran, String customerName);

    PaginationVO pageTransactionList(Map<String, Object> map);

    Tran detail(String id);

    List<TranHistory> getHistoryByTranId(String tranId);

    boolean saveChangeStage(Tran tran);
}
