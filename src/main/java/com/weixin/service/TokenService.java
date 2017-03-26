package com.weixin.service;

import com.weixin.exception.JWeixinException;
import com.weixin.model.AccessToken;

import java.util.function.Supplier;

public interface TokenService {


    /**
     * 获得微信接口调用凭证
     *
     * @param appId     公众号appid
     * @param secret    公众号secret
     * @return token的实例对象
     * @throws JWeixinException 微信访问的异常信息
     */
    AccessToken fetchAccessToken( String appId, String secret) throws JWeixinException;

    /**
     * 获取网页授权取code的url
     *
     * @param appId       公众号appid
     * @param redirectUrl 回调接口地址
     * @param scope       请求类型 base userinfo
     * @param state       状态值，没有请填写空字符串
     * @return 微信网页授权请求地址，请在微信浏览器中访问
     * @throws JWeixinException 微信访问的异常信息
     */
    String fetchPageAccessTokenUrl(String appId, String redirectUrl, String scope, String state) throws JWeixinException;


    /**
     * 通过网页授权获得的code获取网页授权的token
     *
     * @param appId     公众号appid
     * @param secret    公众号secret
     * @param code      网页授权取到的code
     * @return token的实例
     * @throws JWeixinException 微信模块的异常信息
     */
    AccessToken fetchPageAccessToken(String appId, String secret, String code) throws JWeixinException;

    /**
     * 通过用户识别器来获取缓存在服务器中的token
     *
     * @param keySupplier 用户识别器，有调用者提供
     * @param appId    公众号appid
     * @return token实例
     * @throws JWeixinException 微信模块的异常信息
     */
    AccessToken fetchPageAccessToken(Supplier<String> keySupplier, String appId) throws JWeixinException;

    /**
     * 通过制定的refreshToken值来刷新当前token
     *
     * @param appId        公众号appid
     * @param refreshToken 刷新所需要的refreshToken值，正常情况下是从用户识别器中返回的token中获得
     * @return 刷新后的token实例
     * @throws JWeixinException 微信模块的异常信息
     */
    AccessToken refreshPageAccessToken(String appId, String refreshToken) throws JWeixinException;

}
