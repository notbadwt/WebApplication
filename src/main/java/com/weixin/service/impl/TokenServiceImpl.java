package com.weixin.service.impl;

import com.weixin.AccessTokenHolder;
import com.weixin.exception.JWeixinException;
import com.weixin.exception.ParamCheckException;
import com.weixin.exception.WeixinException;
import com.weixin.model.AccessToken;
import com.weixin.service.TokenService;
import com.weixin.util.HttpRequest;
import com.weixin.util.Util;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.function.Supplier;


public class TokenServiceImpl implements TokenService {


    @Override
    public AccessToken fetchAccessToken(String appId, String secret) throws JWeixinException {

        if (Util.paramNullValueCheck(appId, secret)) {
            throw new ParamCheckException();
        }

        AccessToken accessToken;
        accessToken = AccessTokenHolder.getBasicAccessToken();
        if (accessToken != null) {
            return accessToken;
        } else {
            String url = "https://api.weixin.qq.com/cgi-bin/token";
            String param = "grant_type=client_credential&appid=" + appId + "&secret=" + secret;
            String tokenStr = "";
            int expiresIn = 0;

            try (JsonReader jsonReader = HttpRequest.sendGet(url, param)) {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String name = jsonReader.nextName();
                    if ("access_token".equals(name)) {
                        tokenStr = jsonReader.nextString();
                    } else if ("expires_in".equals(name)) {
                        expiresIn = jsonReader.nextInt();
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

            accessToken = AccessTokenHolder.createNewBasicAccessToken(tokenStr, expiresIn);

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
    public AccessToken fetchPageAccessToken(String appId, String secret, String code) throws JWeixinException {
        if (Util.paramNullValueCheck(appId, secret, code)) {
            throw new ParamCheckException();
        }

        AccessToken accessToken = new AccessToken();

        String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
        String tokenParam = "appid=" + appId + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";

        try (JsonReader jsonReader = HttpRequest.sendGet(tokenUrl, tokenParam)) {
            fillPageAccessToken(accessToken, jsonReader);
        } catch (IOException e) {
            throw new JWeixinException(e.getMessage(), e);
        }

        accessToken = AccessTokenHolder.createNewPageAccessToken(accessToken.getOpenid(),accessToken.getAccessToken(),accessToken.getExpiresIn(),accessToken.getRefreshToken(),accessToken.getScope(),accessToken.getOpenid(),accessToken.getUnionid());

        return accessToken;
    }

    @Override
    public AccessToken refreshPageAccessToken(String appId, String refreshToken) throws JWeixinException {

        if (Util.paramNullValueCheck(appId, refreshToken)) {
            throw new ParamCheckException();
        }

        AccessToken accessToken = new AccessToken();
        String refreshUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
        String refreshParam = "appid=" + appId + "&grant_type=refresh_token&refresh_token=" + refreshToken;


        try (JsonReader jsonReader = HttpRequest.sendGet(refreshUrl, refreshParam)) {
            fillPageAccessToken(accessToken, jsonReader);
        } catch (IOException e) {
            throw new JWeixinException(e.getMessage(), e);
        }

        accessToken = AccessTokenHolder.createNewPageAccessToken(accessToken.getOpenid(),accessToken.getAccessToken(),accessToken.getExpiresIn(),accessToken.getRefreshToken(),accessToken.getScope(),accessToken.getOpenid(),accessToken.getUnionid());

        return accessToken;
    }


    private void fillPageAccessToken(AccessToken accessToken, JsonReader jsonReader) throws JWeixinException, IOException {
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
    public AccessToken fetchPageAccessToken(Supplier<String> keySupplier, String appId) throws JWeixinException {
        AccessToken token = AccessTokenHolder.getPageAccessToken(keySupplier.get());
        if (token != null && token.getScope().equals(AccessToken.PAGE_SCOPE_SNSAPI_USERINFO) && !token.isRefreshTokenExpires()) {
            token = refreshPageAccessToken(appId, token.getRefreshToken());
        }
        return token;
    }
}


