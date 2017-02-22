package com.application.weixin.service;

import com.application.weixin.model.Token;

import java.util.function.Supplier;

public interface TokenService {


    Token fetchAccessToken(Class<? extends Token> tokenType, String appId, String secret) throws Exception;

    String fetchPageAccessTokenUrl(String appId, String redirectUrl, String scope, String state) throws Exception;

    Token fetchPageAccessToken(Class<? extends Token> tokenType, String appId, String secret, String code) throws Exception;

    Token fetchPageAccessToken(Supplier<? extends Token> supplier, String appId) throws Exception;

    Token refreshPageAccessToken(Class<? extends Token> tokenType, String appId, String refreshToken) throws Exception;

}
