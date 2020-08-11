package com.tankangya.crm.settings.service.impl;

import com.tankangya.crm.settings.dao.UserDao;
import com.tankangya.crm.settings.entity.User;
import com.tankangya.crm.settings.exception.LoginException;
import com.tankangya.crm.settings.service.UserService;
import com.tankangya.crm.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("myService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    public List<User> queryUser () {
        List<User> list = dao.testSelectUser();
        return list;
    }

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {

        Map<String, String> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);

        User user = dao.login(map);

        if (user == null) {

            throw new LoginException("账号密码错误");

        }

        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();

        if (expireTime.compareTo(currentTime) < 0) {

            throw new LoginException("账户已失效");

        }

        if ("0".equals(user.getLockState())) {

            throw new LoginException("账户已锁定");

        }

        String allowlps = user.getAllowlps();

        if (! allowlps.contains(ip)) {

            throw new LoginException("账户IP地址受限");

        }

        return user;
    }
}
