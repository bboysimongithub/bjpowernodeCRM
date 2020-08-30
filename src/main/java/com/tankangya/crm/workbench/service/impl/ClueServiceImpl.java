package com.tankangya.crm.workbench.service.impl;

import com.tankangya.crm.settings.dao.UserDao;
import com.tankangya.crm.settings.entity.User;
import com.tankangya.crm.workbench.dao.ClueDao;
import com.tankangya.crm.workbench.dao.ClueRemarkDao;
import com.tankangya.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("myClueService")
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueDao clueDao;
    @Autowired
    private ClueRemarkDao clueRemarkDao;
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getUserList() {
        List<User> list = userDao.testSelectUser();
        return list;
    }
}
