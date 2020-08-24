package com.tankangya.crm.workbench.service;


import com.tankangya.crm.vo.PaginationVO;
import com.tankangya.crm.workbench.entity.Activity;
import com.tankangya.crm.workbench.entity.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    public boolean save(Activity a);

    PaginationVO<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] aid);

    Map<String, Object> getUserAndActivity(String id);

    boolean update(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> getRemarkById(String id);

    boolean deleteRemark(String id);

    boolean saveRemark(ActivityRemark activityRemark);

    boolean updateRemark(ActivityRemark activityRemark);
}
