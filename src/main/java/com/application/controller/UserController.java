package com.application.controller;

import com.application.dao.UserDao;
import com.application.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

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
        return userDao.getUserById("1");
    }

    @RequestMapping({"insertUser"})
    @ResponseBody
    public User insertUser() {
        User user = new User();
        user.setId("2");
        user.setUsername("13693097151");
        user.setPassword("123123");
        user.setStatus("1");
        user.setType("1");
        user.setCreateDatetime(new Date().getTime());
        user.setLastLoginDatetime(new Date().getTime());
        userDao.insertUser(user);
        return userDao.getUserById("2");
    }


    @RequestMapping({"updateUser"})
    @ResponseBody
    public User updateUser() {
        User user = userDao.getUserById("2");
        user.setUsername("wangtao");
        userDao.updateUser(user);
        return userDao.getUserById("2");
    }


    @RequestMapping({"removeUser"})
    @ResponseBody
    public User removeUser() {
        userDao.removeUser("2");
        return userDao.getUserById("2");
    }

    @RequestMapping({"deleteUser"})
    @ResponseBody
    public User deleteUser() {
        User user = userDao.getUserById("1");
        userDao.deleteUser("1");
        return user;
    }


}
