package com.tankangya.crm.workbench.dao;

import com.tankangya.crm.workbench.entity.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {

    int saveTran(Tran tran);

    int getTranCount(Map<String, Object> map);

    List getTranList(Map<String, Object> map);

    Tran detail(String id);
}
