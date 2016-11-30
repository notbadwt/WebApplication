package com.application.controller;

import com.application.dao.UserDao;
import com.application.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by WangTao on 2016/11/30 0030.
 */
@Controller
public class UserController {

    private UserDao userDao;

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping({"selectUser"})
    @ResponseBody
    public User selectUser() {
        return userDao.selectUser("1");
    }

}
