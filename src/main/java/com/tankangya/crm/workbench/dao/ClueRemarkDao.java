package com.tankangya.crm.workbench.dao;

import com.tankangya.crm.workbench.entity.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    int getClueRemarkById(String[] para);

    int deleteClueRemarkById(String[] para);

    List<ClueRemark> getClueRemark(String id);

    int deleteClueRemark(String id);

    int updateClueRemark(ClueRemark clueRemark);

    int saveClueRemark(ClueRemark clueRemark);

    int delete(ClueRemark clueRemark);
}
