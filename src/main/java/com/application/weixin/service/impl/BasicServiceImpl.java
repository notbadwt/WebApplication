package com.application.weixin.service.impl;

import com.application.weixin.AccessTokenHolder;
import com.application.weixin.model.AccessToken;
import com.application.weixin.model.Result;
import com.application.weixin.service.BasicService;
import com.application.weixin.util.HttpRequest;
import com.application.weixin.util.Util;
import com.google.gson.stream.JsonReader;


public class BasicServiceImpl implements BasicService {

    @Override
    public AccessToken fetchAccessToken(String appId, String secret) throws Exception {

        if (Util.paramNullValueCheck(appId, secret)) {
            //@TODO 缺少专用的异常类型
            throw new Exception("参数检验失败");
        }

        AccessToken accessToken = AccessTokenHolder.getBasicAccessToken();
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
                        accessToken.setResultStatus(Result.SUCCESS);
                    } else if ("expires_in".equals(name)) {
                        accessToken.setExpiresIn(jsonReader.nextInt());
                        accessToken.setResultStatus(Result.SUCCESS);
                    } else if ("errcode".equals(name)) {
                        accessToken.setErrcode(jsonReader.nextInt());
                        accessToken.setResultStatus(Result.ERROR);
                    } else if ("errmsg".equals(name)) {
                        accessToken.setErrmsg(jsonReader.nextString());
                        accessToken.setResultStatus(Result.ERROR);
                    }
                }
                jsonReader.endObject();
            }
        }
        return accessToken;
    }

    @Override
    public String fetchPageAccessTokenUrl(String appId, String redirectUrl, String scope, String state) throws Exception {
        if (Util.paramNullValueCheck(appId, redirectUrl, scope, state)) {
            //@TODO 缺少专用的异常类型
            throw new Exception("参数检验失败");
        }
        return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri=" + redirectUrl + "&response_type=code&scope=" + scope + "&state=" + state + "#wechat_redirect";
    }

    @Override
    public AccessToken fetchPageAccessToken(String appId, String secret, String code) throws Exception {
        if (Util.paramNullValueCheck(appId, secret, code)) {
            //@TODO 缺少专用的异常类型
            throw new Exception("参数检验失败");
        }

        AccessToken accessToken = AccessTokenHolder.getPageAccessToken();

        String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
        String tokenParam = "appid=" + appId + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";

        try (JsonReader jsonReader = HttpRequest.sendGet(tokenUrl, tokenParam.toString())) {
            fillPageAccessToken(accessToken, jsonReader);
        }


        return accessToken;
    }

    @Override
    public AccessToken refreshPageAccessToken(String appId, String refreshToken) throws Exception {

        if (Util.paramNullValueCheck(appId, refreshToken)) {
            //@TODO 缺少专用的异常类型
            throw new Exception("参数检验失败");
        }

        AccessToken accessToken = AccessTokenHolder.getPageAccessToken();

        String refreshUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
        String refreshParam = "appid=" + appId + "&grant_type=refresh_token&refresh_token=" + refreshToken;


        try (JsonReader jsonReader = HttpRequest.sendGet(refreshUrl, refreshParam)) {
            fillPageAccessToken(accessToken, jsonReader);
        }

        return accessToken;
    }


    private void fillPageAccessToken(AccessToken accessToken, JsonReader jsonReader) throws Exception {
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
                accessToken.setErrcode(jsonReader.nextInt());
            } else if (name.equals("errmsg")) {
                accessToken.setErrmsg(jsonReader.nextString());
            }
        }
        jsonReader.endObject();

        if (accessToken.getErrcode() == null) {
            accessToken.setResultStatus(Result.SUCCESS);
        } else {
            accessToken.setResultStatus(Result.ERROR);
        }
    }
}


