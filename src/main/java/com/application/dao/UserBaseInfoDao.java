package com.application.dao;

import com.application.entity.UserBaseInfo;

/**
 * Created by pgwt on 2017/3/17.
 */
public interface UserBaseInfoDao {

    UserBaseInfo getUserBaseInfoByUserId(String id);

    void insertUserBaseInfo(UserBaseInfo userBaseInfo);

    void updateUserBaseInfo(UserBaseInfo userBaseInfo);

    void deleteUserBaseInfo(String id);

    void removeUserBaseInfo(String id);


}
