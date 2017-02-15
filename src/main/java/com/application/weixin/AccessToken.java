package com.application.weixin;

/**
 * Created by Administrator on 2017/2/15 0015.
 */
public class AccessToken {

    public static final String STATUS_AVAILABLE = "available";  //可用
    public static final String STATUS_PAGE_AUTHORIZATION_REQUIRED = "pageAuthorizationRequired"; // 需要网页授权
    public static final String STATUS_REFRESH_TOKEN_REQUIRED = "refreshTokenRequired"; // 需要刷新token（网页授权使用）

    private Integer tokenExpires; //单位秒
    private Integer refreshTokenExpires = 30 * 24 * 60 * 60;  //单位秒

    private String token;
    private Long createDatetime;
    private String status;


    private String refreshToken;

    private static AccessToken basicAccessToken = null;

    private static AccessToken pageAccessToken = null;

    private AccessToken() {
    }

    public static AccessToken getBasicAccessToken(String appid, String secret) {
        if (basicAccessToken != null && !basicAccessToken.isExpires()) {
            return basicAccessToken;
        } else {
            //@TODO 创建新的accessToken 两种方式创建，第一种，如果basicAccessToken不为null，但是已经过期了，直接调用微信的获取accessToken的接口
            //@TODO 第二种，如果basicAccessToken为null，首先从数据中获取已经存储的最新的accessToken，取回来之后判断是否为空以及是否过期，如果为空或者过期，再调用微信获取accessToken接口
        }
        return null;
    }

    public static AccessToken getPageAccessToken(String appid, String secret) {
        if (pageAccessToken != null && !pageAccessToken.isExpires()) {
            return pageAccessToken;
        } else {
            //@TODO 创建新的accessToken 两种方式创建，第一种，如果basicAccessToken不为null，但是已经过期了，调用微信网页授权接口获得token
            //@TODO 第二种，如果basicAccessToken为null，首先从数据中获取已经存储的最新的accessToken，取回来之后判断是否为空以及是否过期，如果为空或者过期，再调用微信网页授权接口
        }
        return null;
    }

    private Boolean isExpires() {
        Long currentDateTime = System.currentTimeMillis();
        return (currentDateTime.compareTo(createDatetime + (tokenExpires * 1000)) >= 0);
    }

}
