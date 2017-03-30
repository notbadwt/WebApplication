package com.application.web.service.impl;

import com.application.web.dao.UserDao;
import com.application.security.exception.AuthenticationException;
import com.application.security.model.UserDetails;
import com.application.service.UserService;
import com.application.util.DataSourceChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, isolation = Isolation.DEFAULT, transactionManager = "transactionManager")
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    @DataSourceChange(slave = true)
    public UserDetails getUserByUsername(String username) throws AuthenticationException {
        return userDao.getUserByUsername(username);
    }

    @Override
    @DataSourceChange(slave = true)
    public UserDetails getUserByUnionId(String unionId) throws AuthenticationException {
        return userDao.getUserByUnionId(unionId);
    }
}
