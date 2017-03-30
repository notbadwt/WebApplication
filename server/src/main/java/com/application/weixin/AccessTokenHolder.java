package com.application.weixin;

import com.application.weixin.exception.JWeixinException;
import com.application.weixin.model.AccessToken;

import java.util.concurrent.ConcurrentHashMap;


/**
 * Token缓存管理
 */
public class AccessTokenHolder {


    /**
     * 基本授权缓存 全局共享
     */
    private static AccessToken basicAccessToken = null;

    /**
     * 网页授权token的缓存
     */
    private static ConcurrentHashMap<String, AccessToken> tokenCache = new ConcurrentHashMap<>();

    public static AccessToken getPageAccessToken(String key) throws JWeixinException {

        return cloneAccessToken(tokenCache.get(key));
    }


    public static AccessToken createNewPageAccessToken(String key, String tokenStr, Integer expiresIn, String refreshTokenStr, String scope, String openId, String unionid) throws JWeixinException {
        AccessToken pageAccessToken = new AccessToken(expiresIn, tokenStr, refreshTokenStr, scope, openId, unionid);
        tokenCache.put(key, pageAccessToken);
        return cloneAccessToken(pageAccessToken);
    }


    public synchronized static AccessToken createNewBasicAccessToken(String tokenStr, Integer expiresIn) throws JWeixinException {
        if (basicAccessToken != null && basicAccessToken.isAvailable()) {
            return cloneAccessToken(basicAccessToken);
        } else {
            basicAccessToken = new AccessToken(expiresIn, tokenStr);
            return cloneAccessToken(basicAccessToken);
        }
    }


    public static AccessToken getBasicAccessToken() throws JWeixinException {

        if (basicAccessToken != null && basicAccessToken.isAvailable()) {
            return cloneAccessToken(basicAccessToken);
        } else {
            return null;
        }
    }

    private static AccessToken cloneAccessToken(AccessToken accessToken) throws JWeixinException {
        AccessToken result;
        try {
            result = accessToken.clone();
        } catch (Exception e) {
            throw new JWeixinException("系统异常", e);
        }
        return result;
    }
}
