package com.tankangya.crm.workbench.dao;

import com.tankangya.crm.workbench.entity.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {

    int getCountByAids(String[] aid);

    int deleteCountByaids(String[] aid);

    List<ActivityRemark> getRemarkById(String id);

    int deleteRemark(String id);

    int saveRemark(ActivityRemark activityRemark);

    int updateRemark(ActivityRemark activityRemark);
}
