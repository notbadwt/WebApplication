package com.application.web.controller;

import com.application.web.dao.BaseDao;
import com.application.web.dao.UserDao;
import com.application.web.entity.User;
import com.application.service.UserService;
import com.application.logger.ServiceLoggerAspect;
import com.application.security.auth.AbstractSecurity;
import com.application.security.exception.AuthenticationException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.application.security.model.UserDetails;
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

    private BaseDao baseDao;

    @Autowired
    private AbstractSecurity abstractSecurity;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private UserService userService;

    @Autowired
    private ServiceLoggerAspect serviceLoggerAspect;

    @Autowired
    public UserController(BaseDao baseDao, UserDao userDao) {
        this.userDao = userDao;
        this.baseDao = baseDao;
    }

    @RequestMapping({"selectUser"})
    @ResponseBody
    public User selectUser() {
        return userDao.getUserById(2);
    }


    @RequestMapping({"getUserByUsername"})
    @ResponseBody
    public UserDetails getUserByUsername(HttpServletRequest request) throws Exception {
        String userName = request.getParameter("username");
        return userService.getUserByUsername(userName);

    }


    @RequestMapping({"insertUser"})
    @ResponseBody
    public User insertUser() {
        User user = new User();
        user.setId(2);
        user.setUsername("13693097151");
        user.setPassword("123123");
        user.setStatus("1");
        user.setType("1");
        user.setCreateDatetime(new Date().getTime());
        user.setLastLoginDatetime(new Date().getTime());
        baseDao.insertTest(user);
        return userDao.getUserById(2);
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
        User user = userDao.getUserById(2);
        user.setUsername("wangtao");
        userDao.updateUser(user);
        return userDao.getUserById(2);
    }


    @RequestMapping({"removeUser"})
    @ResponseBody
    public User removeUser() {
        userDao.removeUser(2);
        return userDao.getUserById(2);
    }

    @RequestMapping({"deleteUser"})
    @ResponseBody
    public User deleteUser() {
        User user = userDao.getUserById(2);
        userDao.deleteUser(2);
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
            user.setId(2);
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
        return userDao.getUserById(2);
    }


}
