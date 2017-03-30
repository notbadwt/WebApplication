package com.application.web.dao;

import com.application.web.entity.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by pgwt on 2017/3/16.
 */
@Service
public class BaseDao {

    @Autowired
    protected SqlSession sqlSession;

    public void insertTest(User user) {
        sqlSession.insert("com.application.config.mapper.UserMapper.insertUser", user);

    }


}
