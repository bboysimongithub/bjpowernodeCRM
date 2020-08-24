package com.tankangya.crm.workbench.dao;

import com.tankangya.crm.workbench.entity.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {

    public int save(Activity a);

    int getTotalByCondition (Map<String, Object> map);

    List<Activity> getActivityListByCondition (Map<String, Object> map);

    int delete(String[] id);

    Activity getActivityById(String id);

    int update(Activity activity);

    Activity detail(String id);
}
