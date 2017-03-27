package com.application.controller;

import com.application.dao.UserBaseInfoDao;
import com.application.entity.UserBaseInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
@RequestMapping({"/userBaseInfo"})
public class UserBaseInfoController {

    //@TODO 单元测试接口

    private UserBaseInfoDao userBaseInfoDao;

    @Autowired
    public UserBaseInfoController(UserBaseInfoDao userBaseInfoDao) {
        this.userBaseInfoDao = userBaseInfoDao;
    }


    @RequestMapping({"/insert"})
    public String insertUserBaseInfo() {
        Gson gson = new GsonBuilder().create();
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setBirth(new Date());
        userBaseInfo.setCreateDatetime(System.currentTimeMillis());
        userBaseInfo.setId(1);
        userBaseInfo.setUserId(2);
        userBaseInfo.setPhone("13693097151");
        userBaseInfo.setRealName("wangtao");
        userBaseInfo.setSex("1");
        userBaseInfo.setStatus("1");
        userBaseInfoDao.insertUserBaseInfo(userBaseInfo);
        return gson.toJson(userBaseInfo);
    }

    @RequestMapping({"/getById"})
    public String getUserBaseInfoByUserId() {
        Integer id = 2;
        UserBaseInfo userBaseInfo = userBaseInfoDao.getUserBaseInfoByUserId(id);
        Gson gson = new GsonBuilder().create();
        return gson.toJson(userBaseInfo);
    }


    @RequestMapping({"/delete"})
    public String deleteUserBaseInfoById() {
        Integer id = 1;
        try {

            userBaseInfoDao.deleteUserBaseInfo(id);
            return "0";
        } catch (Exception e) {
            return "1";
        }
    }

    @RequestMapping({"/update"})
    public String updateUserBaseInfo() {
        UserBaseInfo userBaseInfo = userBaseInfoDao.getUserBaseInfoByUserId(2);
        userBaseInfo.setRealName("WangTao");
        userBaseInfoDao.updateUserBaseInfo(userBaseInfo);
        Gson gson = new GsonBuilder().create();
        return gson.toJson(userBaseInfo);
    }


    @RequestMapping({"/remove"})
    public String removeUserBaseInfo() {
        userBaseInfoDao.removeUserBaseInfo(1);
        Gson gson = new GsonBuilder().create();
        return gson.toJson(userBaseInfoDao.getUserBaseInfoByUserId(2));
    }


}
