package com.application.controller;

import com.application.dao.UserDao;
import com.application.entity.User;
import com.application.security.auth.AbstractSecurity;
import com.application.security.exception.AuthenticationException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class UserController {

    private UserDao userDao;

    @Autowired
    private AbstractSecurity abstractSecurity;

    @Autowired
    private PlatformTransactionManager transactionManager;

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


    @RequestMapping({"userLogin"})
    @ResponseBody
    public String login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Gson gson = new Gson();
        User user;
        JsonObject jsonObject = new JsonObject();
        try {
            user = (User) abstractSecurity.authenticateByPassword(request, username, password);
        } catch (AuthenticationException ae) {
            jsonObject.addProperty("msg", ae.getMessage());
            return gson.toJson(jsonObject);
        }
        return gson.toJson(user);
    }


    @RequestMapping({"currentUser"})
    @ResponseBody
    public String currentUser(HttpServletRequest request) {
        User user = (User) abstractSecurity.getCurrentUser(request);
        Gson gson = new Gson();
        return gson.toJson(user);
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


    @RequestMapping({"userTransaction"})
    @ResponseBody
    public User userTransaction() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            User user = new User();
            user.setId("3");
            user.setUsername("13693097151");
            user.setPassword("123123");
            user.setStatus("1");
            user.setType("1");
            user.setName("wangtao");
            user.setCreateDatetime(new Date().getTime());
            user.setLastLoginDatetime(new Date().getTime());
            userDao.insertUser(user);
        } catch (Exception ex) {
            transactionManager.rollback(status);
            throw ex;
        }
        transactionManager.commit(status);
        return userDao.getUserById("3");
    }


}
