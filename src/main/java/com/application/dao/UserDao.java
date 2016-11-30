package com.application.dao;

import com.application.entity.User;
import org.mybatis.spring.annotation.MapperScan;

/**
 * Created by WangTao on 2016/11/30 0030.
 *
 */
public interface UserDao {

    User selectUser(String id);

}
