package com.application.weixin.service.impl;

import com.application.weixin.AccessTokenHolder;
import com.application.weixin.model.AccessToken;
import com.application.weixin.model.StringResult;
import com.application.weixin.model.Token;
import com.application.weixin.service.TokenService;
import com.application.weixin.util.HttpRequest;
import com.application.weixin.util.Util;
import com.google.gson.stream.JsonReader;

import java.net.URLEncoder;
import java.util.function.Supplier;


public class TokenServiceImpl implements TokenService {


    @Override
    public Token fetchAccessToken(Class<? extends Token> tokenType, String appId, String secret) throws Exception {

        if (Util.paramNullValueCheck(appId, secret)) {
            //@TODO 缺少专用的异常类型
            throw new Exception("参数检验失败");
        }

        Token accessToken = AccessTokenHolder.getBasicAccessToken(tokenType);
        if (accessToken.getStatus().equals(AccessToken.STATUS_AVAILABLE)) {
            return accessToken;
        } else if (accessToken.getStatus().equals(AccessToken.STATUS_AUTHORIZATION_REQUIiRED)) {
            String url = "https://api.weixin.qq.com/cgi-bin/token";
            String param = "grant_type=client_credential&" + appId + "=" + secret + "&secret=#{secret}";
            try (JsonReader jsonReader = HttpRequest.sendGet(url, param)) {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String name = jsonReader.nextName();
                    if ("access_token".equals(name)) {
                        accessToken.setAccessToken(jsonReader.nextString());
                    } else if ("expires_in".equals(name)) {
                        accessToken.setExpiresIn(jsonReader.nextInt());
                    } else if ("errcode".equals(name)) {
                        //@TODO 抛出相应异常
                    } else if ("errmsg".equals(name)) {
                        //@TODO 抛出相应异常
                    } else {
                        jsonReader.nextString();
                    }
                }
                jsonReader.endObject();
            }
        }
        return accessToken;
    }

    @Override
    public StringResult fetchPageAccessTokenUrl(String appId, String redirectUrl, String scope, String state) throws Exception {
        if (Util.paramNullValueCheck(appId, redirectUrl, scope, state)) {
            //@TODO 缺少专用的异常类型
            throw new Exception("参数检验失败");
        }

        redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");

        return new StringResult("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri=" + redirectUrl + "&response_type=code&scope=" + scope + "&state=" + state + "#wechat_redirect");
    }

    @Override
    public Token fetchPageAccessToken(Class<? extends Token> tokenType, String appId, String secret, String code) throws Exception {
        if (Util.paramNullValueCheck(appId, secret, code)) {
            //@TODO 缺少专用的异常类型
            throw new Exception("参数检验失败");
        }

        Token accessToken = AccessTokenHolder.getPageAccessToken(tokenType);

        String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
        String tokenParam = "appid=" + appId + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";

        try (JsonReader jsonReader = HttpRequest.sendGet(tokenUrl, tokenParam.toString())) {
            fillPageAccessToken(accessToken, jsonReader);
        }

        accessToken.setStatus(Token.STATUS_REFRESH_TOKEN_REQUIRED);

        return accessToken;
    }

    @Override
    public Token fetchPageAccessToken(Supplier<? extends Token> supplier) throws Exception {
        return AccessTokenHolder.getPageAccessToken(supplier);
    }

    @Override
    public Token refreshPageAccessToken(Class<? extends Token> tokenType, String appId, String refreshToken) throws Exception {

        if (Util.paramNullValueCheck(appId, refreshToken)) {
            //@TODO 缺少专用的异常类型
            throw new Exception("参数检验失败");
        }

        Token accessToken = AccessTokenHolder.getPageAccessToken(tokenType);

        String refreshUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
        String refreshParam = "appid=" + appId + "&grant_type=refresh_token&refresh_token=" + refreshToken;


        try (JsonReader jsonReader = HttpRequest.sendGet(refreshUrl, refreshParam)) {
            fillPageAccessToken(accessToken, jsonReader);
        }

        accessToken.setStatus(AccessToken.STATUS_REFRESH_TOKEN_REQUIRED);

        return accessToken;
    }


    private void fillPageAccessToken(Token accessToken, JsonReader jsonReader) throws Exception {
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
                //@TODO 抛出相应的异常
            } else if (name.equals("errmsg")) {
                //@TODO 抛出相应的异常
            } else if (name.equals("unionid")) {
                accessToken.setUnionid(jsonReader.nextString());
            } else {
                jsonReader.nextString();
            }
        }
        jsonReader.endObject();
    }
}


