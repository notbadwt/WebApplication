package com.application.weixin;

import com.application.weixin.model.AccessToken;
import com.application.weixin.model.Result;
import com.application.weixin.model.Token;
import com.application.weixin.service.TokenService;

import java.util.function.Supplier;

/**
 * 微信模块包含很多子模块，如下：
 * 1.基础模块：包含获得授权服务器地址ip网页授权，账号管理等相关接口
 * 2.自定义菜单：在服务号中自定义最下方操作菜单的相关接口组
 * 3.消息管理：在服务号中接收消息以及推送消息的接口组
 * 4.素材管理：在服务号中所发表的文章相关接口组
 * 5.用户管理：服务号粉丝相关接口组，例如获取用户基本信息等
 * 6.数据统计：服务号相关数据的统计信息接口组，用来分析服务号的用户数据
 * 7。。。。。
 * 该类需要提供微信接口可扩展的特性，在微信有新接口以及新标准的时候能够快速并且方便的扩展以及维护，并且需要保证向下兼容
 * <p>
 * 负责调用封装好的各个模块的接口，并且在本层处理异常，并返回该外城对应的错误码以及错误信息
 * 外部调用weixin类对象的时候需要判断返回结果，所有返回结果都是Result类以及子类型，便于外部方便处理返回值，保证返回值类型的统一
 */
public class Weixin {

    public static final WeixinType TYPE_FUWUHAO = WeixinType.FUWUHAO;
    public static final WeixinType TYPE_DINGYUEHAO = WeixinType.DINGYUEHAO;


    private String appId;
    private String secret;
    private WeixinType type;
    private Class<? extends Token> tokenType;

    private TokenService tokenService;

    public Weixin() {
    }

    public Weixin(String appId, String secret, WeixinType type) throws Exception {
        if (appId != null && secret != null && type != null) {
            this.appId = appId;
            this.secret = secret;
            this.type = type;
        }
    }


    public Result<String> getPageAccessTokenUrl(String redirectUrl, String scope, String state) throws Exception {
        return new Result<>(tokenService.fetchPageAccessTokenUrl(appId, redirectUrl, scope, state));
    }

    public Result<Token> getPageAccessToken(String code) throws Exception {
        return new Result<>(tokenService.fetchPageAccessToken(tokenType, appId, secret, code));
    }

    public Result<Token> getPageAccessToken(Supplier<? extends Token> supplier) throws Exception {
        Token currentAccessToken = tokenService.fetchPageAccessToken(supplier, appId);
        if (currentAccessToken.getStatus().equals(AccessToken.STATUS_REFRESH_TOKEN_REQUIRED)) {
            return new Result<>(tokenService.refreshPageAccessToken(tokenType, appId, currentAccessToken.getRefreshToken()));
        } else {
            return new Result<>(currentAccessToken);
        }
    }

    public Result<Token> refreshPageAccessToken(String refreshToken) throws Exception {
        return new Result<>(tokenService.refreshPageAccessToken(tokenType, appId, refreshToken));
    }


    public Result<Token> getAccessToken() throws Exception {
        return new Result<>(tokenService.fetchAccessToken(tokenType, appId, secret));
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public WeixinType getType() {
        return type;
    }

    public void setType(WeixinType type) {
        this.type = type;
    }

    public TokenService getTokenService() {
        return tokenService;
    }

    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public Class<? extends Token> getTokenType() {
        return tokenType;
    }

    public void setTokenType(Class<? extends Token> tokenType) {
        this.tokenType = tokenType;
    }

    public enum WeixinType {

        FUWUHAO("fwh"), DINGYUEHAO("dyh");

        private String name;

        WeixinType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

    }

}
