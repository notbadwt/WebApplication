package com.application.weixin;

import com.application.weixin.model.AccessToken;

import java.util.function.Predicate;

import static com.application.weixin.model.AccessToken.*;

/**
 * Created by Administrator on 2017/2/16 0016.
 */
public class AccessTokenHolder {

    private static final String ACCESS_TOKEN_TYPE_PAGE = "0";
    private static final String ACCESS_TOKEN_TYPE_BASIC = "1";

    private static AccessToken basicAccessToken = null;

    private static AccessToken pageAccessToken = null;

    public static AccessToken getBasicAccessToken() {
        return getAccessToken(ACCESS_TOKEN_TYPE_BASIC);
    }

    public static AccessToken getPageAccessToken() {
        return getAccessToken(ACCESS_TOKEN_TYPE_PAGE);
    }

    private static AccessToken getAccessToken(String accessTokenType) {
        String availableStatus;
        String unavailableStatus;
        AccessToken accessToken;
        Predicate<AccessToken> predicate;

        if (accessTokenType.equals(ACCESS_TOKEN_TYPE_BASIC)) {
            availableStatus = STATUS_AVAILABLE;
            unavailableStatus = STATUS_AUTHORIZATION_REQUIRED;
            accessToken = basicAccessToken;
            predicate = AccessToken::isExpires;
        } else {
            availableStatus = STATUS_REFRESH_TOKEN_REQUIRED;
            unavailableStatus = STATUS_PAGE_AUTHORIZATION_REQUIRED;
            accessToken = pageAccessToken;
            predicate = AccessToken::isRefreshTokenExpires;
        }

        if (accessToken != null && !predicate.test(accessToken)) {
            accessToken.setStatus(availableStatus);
            return accessToken;
        } else if (accessToken == null) {
            accessToken = new AccessToken();
            accessToken.setStatus(unavailableStatus);
            return accessToken;
        } else {
            accessToken.setStatus(unavailableStatus);
            return accessToken;
        }


    }
}
