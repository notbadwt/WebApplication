package com.application.web.dao;

import com.application.web.entity.UserBaseInfo;

/**
 * Created by pgwt on 2017/3/17.
 */
public interface UserBaseInfoDao {

    UserBaseInfo getUserBaseInfoByUserId(Integer id);

    void insertUserBaseInfo(UserBaseInfo userBaseInfo);

    void updateUserBaseInfo(UserBaseInfo userBaseInfo);

    void deleteUserBaseInfo(Integer id);

    void removeUserBaseInfo(Integer id);


}
