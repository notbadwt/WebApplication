package com.application.weixin.service;

import com.application.weixin.model.AccessToken;
import com.application.weixin.model.StringResult;

public interface BasicService {

    String PAGE_SCOPE_SNSAPI_BASE = "snsapi_base";
    String PAGE_SCOPE_SNSAPI_USERINFO = "snsapi_userinfo";

    AccessToken fetchAccessToken(String appId, String secret) throws Exception;

    StringResult fetchPageAccessTokenUrl(String appId, String redirectUrl, String scope, String state) throws Exception;

    AccessToken fetchPageAccessToken(String appId, String secret, String code) throws Exception;

    AccessToken refreshPageAccessToken(String appId, String refreshToken) throws Exception;

}
