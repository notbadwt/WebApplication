package com.application.weixin.model;


public interface Token {

    String STATUS_AVAILABLE = "available";  //可用
    String STATUS_PAGE_AUTHORIZATION_REQUIRED = "pageAuthorizationRequired"; // 需要网页授权
    String STATUS_REFRESH_TOKEN_REQUIRED = "refreshTokenRequired"; // 需要刷新token（网页授权使用）
    String STATUS_AUTHORIZATION_REQUIiRED = "authorization required"; // 需要授权


    Boolean isExpires();

    Boolean isRefreshTokenExpires();

    Integer getExpiresIn();

    void setExpiresIn(Integer expiresIn);

    String getAccessToken();

    void setAccessToken(String accessToken);

    String getStatus();

    void setStatus(String status);

    String getRefreshToken();

    void setRefreshToken(String refreshToken);

    String getScope();

    void setScope(String scope);

    String getOpenid();

    void setOpenid(String openid);

    String getUnionid();

    void setUnionid(String unionid);

}
