package com.application.weixin;

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
 */
public class Weixin {

    public static final WeixinType TYPE_FUWUHAO = WeixinType.FUWUHAO;
    public static final WeixinType TYPE_DINGYUEHAO = WeixinType.DINGYUEHAO;


    private String appId;
    private String secret;
    private WeixinType type;

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
