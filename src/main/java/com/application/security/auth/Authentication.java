package com.application.security.auth;

import com.application.security.model.UserDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/12/19 0019.
 */
public class Authentication {

    public boolean login(HttpServletRequest request ,UserDetails userDetails){
        return false;
    }

    public boolean logout(HttpServletRequest request){
        return false;
    }

    public boolean hasPermission(HttpServletRequest request){
        return false;
    }

    public UserDetails getCurrentUser(HttpServletRequest request){
        return null;
    }

}
