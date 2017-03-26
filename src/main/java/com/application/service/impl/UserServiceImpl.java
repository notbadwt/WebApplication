package com.application.service.impl;

import com.application.dao.UserDao;
import com.security.exception.AuthenticationException;
import com.security.model.UserDetails;
import com.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, isolation = Isolation.DEFAULT)
@Component
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
