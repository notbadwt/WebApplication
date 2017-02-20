package com.application.weixin.model;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AccessToken implements Token {

    private static final Long refreshTokenExpires = 30 * 24 * 50 * 60L;  //单位秒

    private Integer expiresIn; //单位秒
    private String accessToken;
    private String refreshToken;
    private String scope;
    private String openid;
    private String unionid;


    private Long createDatetime;
    private String status;

    public AccessToken() {
        createDatetime = System.currentTimeMillis();
    }

    @Override
    public Boolean isExpires() {
        Long currentDateTime = System.currentTimeMillis();
        return (currentDateTime.compareTo(createDatetime + (expiresIn * 1000L)) >= 0);
    }

    @Override
    public Boolean isRefreshTokenExpires() {
        Long currentDatetime = System.currentTimeMillis();
        Long endDatetime = createDatetime + (refreshTokenExpires * 1000L);
        System.out.println("currentDatetime : " + currentDatetime);
        System.out.println("accessTokenEndTime : " + endDatetime);
        Boolean result = currentDatetime.compareTo(endDatetime) >= 0;
        System.out.println("compareToResult : " + result);
        return result;
    }

    @Override
    public Integer getExpiresIn() {
        return expiresIn;
    }

    @Override
    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String getScope() {
        return scope;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String getOpenid() {
        return openid;
    }

    @Override
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Override
    public String getUnionid() {
        return unionid;
    }

    @Override
    public void setUnionid(String unionid) {
        this.unionid = unionid;
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
}
