package com.application.dao;

import com.application.entity.User;

/**
 * Created by WangTao on 2016/11/30 0030.
 */
public interface UserDao {

    User getUserById(String id);

    void insertUser(User user);

    void updateUser(User user);

    void deleteUser(String id);

    void removeUser(String id);

}
