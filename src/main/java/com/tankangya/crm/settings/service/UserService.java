package com.tankangya.crm.settings.service;

import com.tankangya.crm.settings.entity.User;
import com.tankangya.crm.settings.exception.LoginException;

import java.util.List;

public interface UserService {

    public List<User> queryUser ();

     public User login(String loginAct, String loginPwd, String ip) throws LoginException;
}
