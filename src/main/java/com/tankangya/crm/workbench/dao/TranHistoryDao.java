package com.tankangya.crm.workbench.dao;

import com.tankangya.crm.workbench.entity.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int savetranHistory(TranHistory tranHistory);

    List<TranHistory> getHistoryByTranId(String tranId);
}
