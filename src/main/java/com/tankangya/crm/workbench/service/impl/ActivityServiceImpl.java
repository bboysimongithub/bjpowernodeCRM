package com.tankangya.crm.workbench.service.impl;

import com.tankangya.crm.settings.dao.UserDao;
import com.tankangya.crm.settings.entity.User;
import com.tankangya.crm.workbench.dao.ActivityDao;
import com.tankangya.crm.workbench.entity.Activity;
import com.tankangya.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("workbenchService")
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDao dao;
    @Autowired
    private UserDao userDao;

    public List<User> queryUser () {
        List<User> list = userDao.testSelectUser();
        return list;
    }


    @Override
    public boolean save(Activity a) {
        boolean flag = false;

        int number = dao.save(a);

        if (number != 0) {
            flag = true;
        }
        return flag;
    }
}
