package com.EasyRide.dao;

import com.EasyRide.entity.User;

// UserDao.java
public interface UserDao {
    User getUserByUsername(String username);
    User addUser(User user);
    // 其他数据库操作方法
}
