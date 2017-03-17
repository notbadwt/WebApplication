package com.application.controller;

import com.application.dao.UserDocumentDao;
import com.application.entity.User;
import com.application.entity.UserDocument;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/userDocument"})
public class UserDocumentController {


    private UserDocumentDao userDocumentDao;

    @Autowired
    public UserDocumentController(UserDocumentDao userDocumentDao) {
        this.userDocumentDao = userDocumentDao;
    }

    @RequestMapping({"/insert"})
    public String insertUserDocument() {
        UserDocument userDocument = new UserDocument();
        userDocument.setId("1");
        userDocument.setStatus("1");
        userDocument.setUserId("2");
        userDocument.setContent("这事一个新的文档");
        userDocument.setCreateDatetime(System.currentTimeMillis());
        userDocumentDao.insertUserDocument(userDocument);
        Gson gson = new GsonBuilder().create();
        return gson.toJson(userDocument);
    }

    @RequestMapping({"/listByUserId"})
    public String listUserDocumentByUserId() {
        String userId = "2";
        List<UserDocument> result = userDocumentDao.listUserDocumentByUserId(userId);
        Gson gson = new GsonBuilder().create();
        return gson.toJson(result);
    }

    @RequestMapping({"/update"})
    public String updateUserDocument() {
        UserDocument userDocument = userDocumentDao.getUserDocumentById("1");
        if (userDocument != null) {
            userDocument.setContent("这事一个更改后的文档");
        }
        userDocumentDao.updateUserDocument(userDocument);
        Gson gson = new GsonBuilder().create();
        return gson.toJson(userDocument);
    }

    @RequestMapping({"/delete"})
    public String deleteUserDocument() {
        try {
            userDocumentDao.deleteUserDocument("1");
            return "0";
        } catch (Exception e) {
            return "1";
        }
    }


    @RequestMapping({"/remove"})
    public String removeUserDocument() {
        userDocumentDao.removeUserDocument("1");
        UserDocument userDocument = userDocumentDao.getUserDocumentById("1");
        Gson gson = new GsonBuilder().create();
        return gson.toJson(userDocument);
    }

}
