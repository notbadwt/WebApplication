package com.application.weixin.service.impl;

import com.application.weixin.AccessTokenHolder;
import com.application.weixin.exception.JWeixinException;
import com.application.weixin.exception.ParamCheckException;
import com.application.weixin.exception.WeixinException;
import com.application.weixin.model.AccessToken;
import com.application.weixin.model.Token;
import com.application.weixin.service.TokenService;
import com.application.weixin.util.HttpRequest;
import com.application.weixin.util.Util;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.function.Supplier;


public class TokenServiceImpl implements TokenService {


    @Override
    public Token fetchAccessToken(Class<? extends Token> tokenType, String appId, String secret) throws JWeixinException {

        if (Util.paramNullValueCheck(appId, secret)) {
            throw new ParamCheckException();
        }

        Token accessToken;
        try {
            accessToken = AccessTokenHolder.getBasicAccessToken(tokenType);
        } catch (Exception e) {
            throw new JWeixinException(e.getMessage(), e);
        }
        if (accessToken.getStatus().equals(AccessToken.STATUS_AVAILABLE)) {
            return accessToken;
        } else if (accessToken.getStatus().equals(AccessToken.STATUS_AUTHORIZATION_REQUIiRED)) {
            String url = "https://api.weixin.qq.com/cgi-bin/token";
            String param = "grant_type=client_credential&appid=" + appId + "&secret=" + secret;
            try (JsonReader jsonReader = HttpRequest.sendGet(url, param)) {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String name = jsonReader.nextName();
                    if ("access_token".equals(name)) {
                        accessToken.setAccessToken(jsonReader.nextString());
                    } else if ("expires_in".equals(name)) {
                        accessToken.setExpiresIn(jsonReader.nextInt());
                    } else if ("errcode".equals(name)) {
                        throw new WeixinException(jsonReader.nextInt());
                    } else {
                        jsonReader.nextString();
                    }
                }
                jsonReader.endObject();
            } catch (IOException e) {
                throw new JWeixinException(e.getMessage(), e);
            }
        }
        return accessToken;
    }

    @Override
    public String fetchPageAccessTokenUrl(String appId, String redirectUrl, String scope, String state) throws JWeixinException {
        if (Util.paramNullValueCheck(appId, redirectUrl, scope, state)) {
            throw new ParamCheckException();
        }

        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (Exception e) {
            throw new JWeixinException(e.getMessage(), e);
        }

        return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri=" + redirectUrl + "&response_type=code&scope=" + scope + "&state=" + state + "#wechat_redirect";
    }

    @Override
    public Token fetchPageAccessToken(Class<? extends Token> tokenType, String appId, String secret, String code) throws JWeixinException {
        if (Util.paramNullValueCheck(appId, secret, code)) {
            throw new ParamCheckException();
        }

        Token accessToken;
        try {
            accessToken = AccessTokenHolder.getPageAccessToken(tokenType);
        } catch (Exception e) {
            throw new JWeixinException(e.getMessage(), e);
        }

        String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
        String tokenParam = "appid=" + appId + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";

        try (JsonReader jsonReader = HttpRequest.sendGet(tokenUrl, tokenParam)) {
            fillPageAccessToken(accessToken, jsonReader);
        } catch (IOException e) {
            throw new JWeixinException(e.getMessage(), e);
        }

        accessToken.setStatus(Token.STATUS_REFRESH_TOKEN_REQUIRED);

        return accessToken;
    }

    @Override
    public Token refreshPageAccessToken(Class<? extends Token> tokenType, String appId, String refreshToken) throws JWeixinException {

        if (Util.paramNullValueCheck(appId, refreshToken)) {
            throw new ParamCheckException();
        }

        Token accessToken = AccessTokenHolder.getPageAccessToken(AccessToken::new);

        String refreshUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
        String refreshParam = "appid=" + appId + "&grant_type=refresh_token&refresh_token=" + refreshToken;


        try (JsonReader jsonReader = HttpRequest.sendGet(refreshUrl, refreshParam)) {
            fillPageAccessToken(accessToken, jsonReader);
        } catch (IOException e) {
            throw new JWeixinException(e.getMessage(), e);
        }

        accessToken.setStatus(AccessToken.STATUS_REFRESH_TOKEN_REQUIRED);

        return accessToken;
    }


    private void fillPageAccessToken(Token accessToken, JsonReader jsonReader) throws JWeixinException, IOException {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals("access_token")) {
                accessToken.setAccessToken(jsonReader.nextString());
            } else if (name.equals("expires_in")) {
                accessToken.setExpiresIn(jsonReader.nextInt());
            } else if (name.equals("refresh_token")) {
                accessToken.setRefreshToken(jsonReader.nextString());
            } else if (name.equals("openid")) {
                accessToken.setOpenid(jsonReader.nextString());
            } else if (name.equals("scope")) {
                accessToken.setScope(jsonReader.nextString());
            } else if (name.equals("errcode")) {
                throw new WeixinException(jsonReader.nextInt());
            } else if (name.equals("unionid")) {
                accessToken.setUnionid(jsonReader.nextString());
            } else {
                jsonReader.nextString();
            }
        }
        jsonReader.endObject();
    }

    @Override
    public Token fetchPageAccessToken(Supplier<? extends Token> supplier, String appId) throws JWeixinException {
        Token token = AccessTokenHolder.getPageAccessToken(supplier);
        if (token != null && token.getScope().equals(Token.PAGE_SCOPE_SNSAPI_USERINFO) && !token.isRefreshTokenExpires()) {
            token = refreshPageAccessToken(token.getClass(), appId, token.getRefreshToken());
        }
        return token;
    }
}


