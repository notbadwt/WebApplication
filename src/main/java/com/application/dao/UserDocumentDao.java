package com.application.dao;

import com.application.entity.UserDocument;

import javax.swing.text.Document;
import java.util.List;

/**
 * Created by pgwt on 2017/3/17.
 */
public interface UserDocumentDao {

    List<UserDocument> listUserDocumentByUserId(String id);

    UserDocument getUserDocumentById(String id);

    void insertUserDocument(UserDocument userDocument);

    void updateUserDocument(UserDocument userDocument);

    void deleteUserDocument(String id);

    void removeUserDocument(String id);

}
