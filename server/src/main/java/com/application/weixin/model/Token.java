package com.application.weixin.model;


/**
 * token是个多个线程共有的对象，需要保证其线程安全性，或者，禁止外部程序修改其内容
 * 有关于token的操作需要做一些调整，
 * 1.把token相关的域变为不可变的
 * 2.去掉status属性，某个token对象的状态是根据系统当前时间以及token对象自身的状态决定，token对象是个不可变状态对象
 */
public interface Token {

    String STATUS_AVAILABLE = "available";  //可用
    String STATUS_PAGE_AUTHORIZATION_REQUIRED = "pageAuthorizationRequired"; // 需要网页授权
    String STATUS_REFRESH_TOKEN_REQUIRED = "refreshTokenRequired"; // 需要刷新token（网页授权使用）
    String STATUS_AUTHORIZATION_REQUIiRED = "authorization required"; // 需要授权

    String PAGE_SCOPE_SNSAPI_BASE = "snsapi_base";
    String PAGE_SCOPE_SNSAPI_USERINFO = "snsapi_userinfo";



    Boolean isExpires();

    Boolean isRefreshTokenExpires();

    Integer getExpiresIn();

    String getAccessToken();

    String getRefreshToken();

    String getScope();

    String getOpenid();

    String getUnionid();


}
