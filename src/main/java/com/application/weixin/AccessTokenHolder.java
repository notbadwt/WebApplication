package com.application.weixin;

import com.application.weixin.model.Token;

import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.application.weixin.model.Token.*;


/**
 * AccessTokenHolder的最新职责
 * 1.负责管理basicAccessToken的统一管理与共享功能
 * 2.负责管理PageAccessToken的业务处理功能，提供相应的回调接口，每一个PageAccessToken都是一个临时的token，只是通过用户来识别
 * <p>
 * AccessTokenHolder的之前的职责：
 * 1.提供BasicAccessToken 以及 PageAccessToken的初始化以及单例的功能
 * 2.判断当前Token的不同状态来确认Token应该保持的状态，如果是空就返回一个新的初始化的token，状态时unavailable，如果已经过期，将状态改为unavailable并且返回
 * <p>
 * AccessTokenHolder的改变
 * 1.保留basicAccessToken的原有功能，将PageAccessToken放入新的接口中
 */
public class AccessTokenHolder {


    private static Token basicAccessToken = null;


    public static Token getPageAccessToken(Supplier<? extends Token> accessTokenSupplier) {
        return accessTokenSupplier.get();
    }

    public static Token getPageAccessToken(Class<? extends Token> tokenType) throws Exception {
        Token token = tokenType.newInstance();
        token.setStatus(STATUS_PAGE_AUTHORIZATION_REQUIRED);
        return token;
    }

    public static Token getBasicAccessToken(Class<? extends Token> tokenType) throws Exception {
        String availableStatus;
        String unavailableStatus;
        Token accessToken;

        Predicate<? super Token> predicate;

        availableStatus = STATUS_AVAILABLE;
        unavailableStatus = STATUS_AUTHORIZATION_REQUIiRED;
        accessToken = basicAccessToken;
        predicate = Token::isExpires;


        if (accessToken != null && accessToken.getStatus().equals(availableStatus) && !predicate.test(accessToken)) {
            return accessToken;
        } else if (accessToken == null) {
            accessToken = tokenType.newInstance();
            accessToken.setStatus(unavailableStatus);
            basicAccessToken = accessToken;
            return accessToken;
        } else {
            accessToken.setStatus(unavailableStatus);
            return accessToken;
        }


    }
}
