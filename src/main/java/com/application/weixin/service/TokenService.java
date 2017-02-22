package com.application.weixin.service;

import com.application.weixin.exception.JWeixinException;
import com.application.weixin.model.Token;

import java.util.function.Supplier;

public interface TokenService {


    Token fetchAccessToken(Class<? extends Token> tokenType, String appId, String secret) throws JWeixinException;

    String fetchPageAccessTokenUrl(String appId, String redirectUrl, String scope, String state) throws JWeixinException;

    Token fetchPageAccessToken(Class<? extends Token> tokenType, String appId, String secret, String code) throws JWeixinException;

    Token fetchPageAccessToken(Supplier<? extends Token> supplier, String appId) throws JWeixinException;

    Token refreshPageAccessToken(Class<? extends Token> tokenType, String appId, String refreshToken) throws JWeixinException;

}
