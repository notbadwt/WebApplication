package com.application.weixin.model;


public class AccessToken extends Result {

    public static final String STATUS_AVAILABLE = "available";  //可用
    public static final String STATUS_PAGE_AUTHORIZATION_REQUIRED = "pageAuthorizationRequired"; // 需要网页授权
    public static final String STATUS_REFRESH_TOKEN_REQUIRED = "refreshTokenRequired"; // 需要刷新token（网页授权使用）
    public static final String STATUS_AUTHORIZATION_REQUIiRED = "authorization required"; // 需要授权

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


    public Boolean isExpires() {
        Long currentDateTime = System .currentTimeMillis();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
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


    @Override
    public String toString() {
        if (getResultStatus().equals(Result.SUCCESS)) {
            return "[access_token:" + accessToken + "]\n[refresh_token:" + refreshToken + "]\n[openid:" + openid + "]";
        } else {
            return "[errcode:" + getErrcode() + "]\n[errmsg:" + getErrmsg() + "]";
        }
    }


    public static void main(String[] args) {

        Integer a = 1;
        Integer b = 30 * 24 * 60 * 60 * 1000;
        Integer c = a + b;
        System.out.println(b);
        System.out.println(c);

    }
}
