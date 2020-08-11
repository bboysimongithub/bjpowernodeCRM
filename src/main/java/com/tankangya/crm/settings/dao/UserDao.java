package com.tankangya.crm.settings.dao;

import com.tankangya.crm.settings.entity.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    public List<User> testSelectUser ();


    public User login(Map<String, String> map);
}
