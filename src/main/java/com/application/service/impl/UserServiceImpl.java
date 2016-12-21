package com.application.service.impl;

import com.application.dao.UserDao;
import com.application.security.exception.AuthenticationException;
import com.application.security.model.UserDetails;
import com.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public UserDetails getUserByUsername(String username) throws AuthenticationException {
        return userDao.getUserByUsername(username);
    }

    @Override
    public UserDetails getUserByUnionId(String unionId) throws AuthenticationException {
        return userDao.getUserByUnionId(unionId);
    }
}
