package com.tankangya.crm.workbench.service.impl;

import com.tankangya.crm.settings.dao.UserDao;
import com.tankangya.crm.settings.entity.User;
import com.tankangya.crm.vo.PaginationVO;
import com.tankangya.crm.workbench.dao.ActivityDao;
import com.tankangya.crm.workbench.dao.ActivityRemarkDao;
import com.tankangya.crm.workbench.entity.Activity;
import com.tankangya.crm.workbench.entity.ActivityRemark;
import com.tankangya.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("workbenchService")
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDao dao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ActivityRemarkDao remarkDao;

    public List<User> queryUser () {
        List<User> list = userDao.testSelectUser();
        return list;
    }


    @Override
    public boolean saveActivity(Activity a) {
        boolean flag = false;

        int number = dao.save(a);

        if (number != 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {
        //取得total
        int total = dao.getTotalByCondition(map);
        //取得dataList
        List<Activity> datalist = dao.getActivityListByCondition(map);
        //创建一个vo对象，将total和dataList封装到vo中
        PaginationVO paginationVO = new PaginationVO();
        paginationVO.setTotal(total);
        paginationVO.setDatalist(datalist);


        return paginationVO;
    }

    @Override
    public boolean deleteActivity(String[] ids) {
        boolean flag = true;

        //查询出需要删除的备注的数量
        int count1 = remarkDao.getCountByAids(ids);

        //删除备注，返回受到影响的条数（实际删除的数量）
        int count2 = remarkDao.deleteCountByaids(ids);

        if(count1!=count2){

            flag = false;

        }

        //删除市场活动
        int count3 = dao.delete(ids);
        if(count3!=ids.length){

            flag = false;

        }

        return flag;
    }

    @Override
    public Map<String, Object> getUserAndActivity(String id) {
        //取出User
        List<User> uList = userDao.testSelectUser();
        //查询出单条activity
        Activity a = dao.getActivityById(id);
        //封装到map
        Map<String, Object> map = new HashMap<>();
        map.put("uList", uList);
        map.put("a", a);
        return map;
    }

    @Override
    public boolean updateActivity(Activity activity) {
        boolean flag = false;

        int count = dao.update(activity);
        if (count != 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public Activity detail(String id) {

       return dao.detail(id);

    }

    @Override
    public List<ActivityRemark> getRemarkById(String id) {

        List<ActivityRemark> list = remarkDao.getRemarkById(id);
        return list;
    }

    @Override
    public boolean deleteRemark(String id) {
        boolean flag = false;

        int count = remarkDao.deleteRemark(id);

        if (count != 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark activityRemark) {
        boolean flag = false;

        int count = remarkDao.saveRemark(activityRemark);

        if (count != 0) {
            flag = true;
        }

        return flag;
    }

    @Override
    public boolean updateRemark(ActivityRemark activityRemark) {

        boolean flag = false;

        int count = remarkDao.updateRemark(activityRemark);

        if (count != 0) {
            flag = true;
        }

        return flag;
    }

    @Override
    public List<Activity> getActivityListByClueId(String clueId) {
        List<Activity> list = dao.getActivityListByClueId(clueId);
        return list;
    }

    @Override
    public List<Activity> getActivityListByNameAndNotClueId(String id, String aname) {

        List<Activity> list = dao.getActivityListByNameAndNotClueId(id,aname);
        return list;
    }


}
