package com.tankangya.crm.workbench.dao;

import com.tankangya.crm.workbench.entity.Activity;
import org.apache.ibatis.annotations.Param;

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

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListByNameAndNotClueId(@Param("clueId")String id,
                                                     @Param("name") String aname);

    List<Activity> getActivityList(String name);
}
