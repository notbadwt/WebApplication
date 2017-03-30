package com.application.weixin;

import com.application.weixin.exception.JWeixinException;
import com.application.weixin.model.AccessToken;
import com.application.weixin.model.Result;
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
 * 外部调用weixin类对象的时候需要判断返回结果，所有返回结果都是Result容器类，便于外部方便处理返回值，保证返回值类型的统一
 */
//@TODO 所有异常处理的位置都要做日志记录
public class Weixin {

    //@TODO 用于添加权限，每种公众号的访问接口权限不一样
    public static final WeixinType TYPE_FUWUHAO = WeixinType.FUWUHAO;
    public static final WeixinType TYPE_DINGYUEHAO = WeixinType.DINGYUEHAO;


    private String appId;
    private String secret;
    private WeixinType type;

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


    /**
     * 获取网页授权获取weixin_code的请求地址
     *
     * @param redirectUrl 回调地址
     * @param scope       请求类型
     * @param state       状态值，如果没有就填写空字符串
     * @return 请求链接
     */
    public Result<String> getPageAccessTokenUrl(String redirectUrl, String scope, String state) {
        String codeUrl;
        try {
            codeUrl = tokenService.fetchPageAccessTokenUrl(appId, redirectUrl, scope, state);
            return new Result<>(codeUrl);
        } catch (JWeixinException e) {
            return new Result<>(e.getErrcode(), e.getErrmsg());
        }

    }

    /**
     * 通过网页授权获取的code来获取网页授权的accessToken
     *
     * @param code 用户授权的code
     * @return 借口调用凭证以及一些附带信息
     */
    public Result<AccessToken> getPageAccessToken(String code) {
        AccessToken token;
        try {
            token = tokenService.fetchPageAccessToken( appId, secret, code);
            return new Result<>(token);
        } catch (JWeixinException e) {
            return new Result<>(e.getErrcode(), e.getErrmsg());
        }

    }


    /**
     * 通过提供“用户识别器”来获取缓冲中的某个用户的Token，如果该token是用户获取用户信息，并且refresh_token没有过期，那么久直接刷新token，然后返回新的token
     * 注意，这里返回的Token同步更新到系统的缓存中替换之前的token
     *
     * @param keySupplier 用户识别器的一个函数式接口
     * @return 缓存的或者经过刷新的token
     */
    public Result<AccessToken> getPageAccessToken(Supplier<String> keySupplier) {
        AccessToken currentAccessToken;
        try {
            currentAccessToken = tokenService.fetchPageAccessToken(keySupplier, appId);
                return new Result<>(currentAccessToken);
        } catch (JWeixinException e) {
            return new Result<>(e.getErrcode(), e.getErrmsg());
        }
    }


    /**
     * 通过给定的refreshToken刷新当前的AccessToken
     *
     * @param refreshToken refreshToken的值，可以从网页授权获取的token对象中获得，或者从系统缓存的token中获得
     * @return 更新完成的token对象，如果出错，返回形式将以错误码和错误信息返回，该类所有对外的微信接口的返回格式都是如此，其他接口不再赘述
     */
    public Result<AccessToken> refreshPageAccessToken(String refreshToken) {
        AccessToken token;
        try {
            token = tokenService.refreshPageAccessToken(appId, refreshToken);
            return new Result<>(token);
        } catch (JWeixinException e) {
            return new Result<>(e.getErrcode(), e.getErrmsg());
        }
    }


    /**
     * 获得基本的微信接口调用凭证（不需要网页授权，如果使用用户相关操作，必须是已经关注公众号的微信用户才可以使用该凭证，否则一律需要网页授权）
     *
     * @return 基本调用凭证对象
     */
    public Result<AccessToken> getAccessToken() {
        AccessToken token;
        try {
            token = tokenService.fetchAccessToken( appId, secret);
            return new Result<>(token);
        } catch (JWeixinException e) {
            return new Result<>(e.getErrcode(), e.getErrmsg());
        }
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
