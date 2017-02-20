package com.application.weixin.service;

import com.application.weixin.model.StringResult;
import com.application.weixin.model.Token;

import java.util.function.Supplier;

public interface TokenService {

    String PAGE_SCOPE_SNSAPI_BASE = "snsapi_base";
    String PAGE_SCOPE_SNSAPI_USERINFO = "snsapi_userinfo";

    Token fetchAccessToken(Class<? extends Token> tokenType, String appId, String secret) throws Exception;

    StringResult fetchPageAccessTokenUrl(String appId, String redirectUrl, String scope, String state) throws Exception;

    Token fetchPageAccessToken(Class<? extends Token> tokenType, String appId, String secret, String code) throws Exception;

    Token fetchPageAccessToken(Supplier<? extends Token> supplier) throws Exception;

    Token refreshPageAccessToken(Class<? extends Token> tokenType, String appId, String refreshToken) throws Exception;

}
