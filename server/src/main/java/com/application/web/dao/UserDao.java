package com.application.web.dao;

import com.application.web.entity.User;

/**
 * Created by WangTao on 2016/11/30 0030.
 */
public interface UserDao {

    User getUserById(Integer id);

    User getUserByUsername(String username);

    User getUserByUnionId(String unionId);

    void insertUser(User user);

    void updateUser(User user);

    void deleteUser(Integer id);

    void removeUser(Integer id);

}
