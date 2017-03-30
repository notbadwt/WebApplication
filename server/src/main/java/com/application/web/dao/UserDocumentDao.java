package com.application.web.dao;

import com.application.web.entity.UserDocument;

import java.util.List;

/**
 * Created by pgwt on 2017/3/17.
 */
public interface UserDocumentDao {

    List<UserDocument> listUserDocumentByUserId(Integer id);

    List<UserDocument> listUserDocumentByUserFriends(Integer id);

    UserDocument getUserDocumentById(Integer id);

    void insertUserDocument(UserDocument userDocument);

    void updateUserDocument(UserDocument userDocument);

    void deleteUserDocument(Integer id);

    void removeUserDocument(Integer id);

}
