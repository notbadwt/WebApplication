package com.application.weixin;

import com.application.weixin.model.AccessToken;
import com.application.weixin.model.StringResult;
import com.application.weixin.service.BasicService;

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

    private BasicService basicService;

    public Weixin() {
        if (getAppId() != null && getSecret() != null && getType() != null) {
            login();
        }
    }

    public Weixin(String appId, String secret, WeixinType type) {
        if (appId != null && secret != null && type != null) {
            this.appId = appId;
            this.secret = secret;
            this.type = type;
            this.login();
        }
    }


    public StringResult getPageAccessTokenUrl(String redirectUrl, String scope, String state) throws Exception {
        return basicService.fetchPageAccessTokenUrl(appId, redirectUrl, scope, state);
    }

    public AccessToken getPageAccessToken(String code) throws Exception {
        return basicService.fetchPageAccessToken(appId, secret, code);
    }

    public AccessToken getPageAccessToken() throws Exception {
        AccessToken currentAccessToken = AccessTokenHolder.getPageAccessToken();
        if (currentAccessToken.getStatus().equals(AccessToken.STATUS_REFRESH_TOKEN_REQUIRED)) {
            return basicService.refreshPageAccessToken(appId, currentAccessToken.getRefreshToken());
        } else {
            return currentAccessToken;
        }
    }

    public AccessToken refreshPageAccessToken(String refreshToken) throws Exception {
        return basicService.refreshPageAccessToken(appId, refreshToken);
    }

    public AccessToken getAccessToken() throws Exception {
        return basicService.fetchAccessToken(appId, secret);
    }


    //@TODO 微信的基础授权以及网页授权

    /**
     * 该方法用户在调用微信相关接口之前获得微信的授权，这里已login命名，登录的账号就是appid以及secret属性
     *
     * @return 返回操作结果的码，参考微信接口码定义类
     */
    public String login() {
        return null;
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

    public BasicService getBasicService() {
        return basicService;
    }

    public void setBasicService(BasicService basicService) {
        this.basicService = basicService;
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
