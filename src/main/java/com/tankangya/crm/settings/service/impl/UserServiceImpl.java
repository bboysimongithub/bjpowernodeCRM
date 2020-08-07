package com.tankangya.crm.settings.service.impl;

import com.tankangya.crm.settings.dao.UserDao;
import com.tankangya.crm.settings.entity.User;
import com.tankangya.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("myService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    public List<User> queryUser () {
        List<User> list = dao.testSelectUser();
        return list;
    }
}
