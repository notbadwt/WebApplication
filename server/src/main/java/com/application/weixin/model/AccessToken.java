package com.application.weixin.model;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AccessToken implements Cloneable {

    private static final Long refreshTokenExpires = 30 * 24 * 50 * 60L;  //单位秒

    public static final String TYPE_BASIC = "basic";
    public static final String TYPE_PAGE = "page";

    public static final String PAGE_SCOPE_SNSAPI_BASE = "snsapi_base";
    public static final String PAGE_SCOPE_SNSAPI_USERINFO = "snsapi_userinfo";



    private Integer expiresIn; //单位秒
    private String accessToken;
    private Long createDatetime;

    private String scope;
    private String openid;
    private String unionid;
    private String refreshToken;


    public AccessToken() {
    }

    public AccessToken(Integer expiresIn, String accessToken, String refreshToken, String scope, String openid, String unionid) {
        this.expiresIn = expiresIn;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.scope = scope;
        this.openid = openid;
        this.unionid = unionid;
        this.createDatetime = System.currentTimeMillis();
    }

    public AccessToken(Integer expiresIn, String accessToken) {
        this.expiresIn = expiresIn;
        this.accessToken = accessToken;
        this.createDatetime = System.currentTimeMillis();
    }


    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Long createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Boolean isExpires() {
        Long currentDateTime = System.currentTimeMillis();
        return (currentDateTime.compareTo(createDatetime + (expiresIn * 1000L)) >= 0);
    }

    public Boolean isRefreshTokenExpires() {
        Long currentDatetime = System.currentTimeMillis();
        Long endDatetime = createDatetime + (refreshTokenExpires * 1000L);
        System.out.println("currentDatetime : " + currentDatetime);
        System.out.println("accessTokenEndTime : " + endDatetime);
        Boolean result = currentDatetime.compareTo(endDatetime) >= 0;
        System.out.println("compareToResult : " + result);
        return result;
    }


    //@TODO 还没有实现
    public boolean isAvailable() {
        return false;
    }

    //@TODO 确认token类型
    public String getType() {
        return "";
    }


    @Override
    public String toString() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }


    public static void main(String[] args) {

        Integer a = 1;
        Integer b = 30 * 24 * 60 * 60 * 1000;
        Integer c = a + b;
        System.out.println(b);
        System.out.println(c);

    }

    @Override
    public AccessToken clone() throws CloneNotSupportedException {
        return (AccessToken) super.clone();
    }
}
