package com.application.controller;


import com.application.weixin.AccessTokenHolder;
import com.application.weixin.Weixin;
import com.application.weixin.model.AccessToken;
import com.application.weixin.model.Token;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestWeixinController {


    private Weixin weixin;

    @Autowired
    public TestWeixinController(Weixin weixin) {
        this.weixin = weixin;
    }

    @RequestMapping({"/getOpenidResult"})
    public String getOpenIdResult(HttpServletRequest request, Model model) throws Exception {
        String weixinCode = request.getParameter("code");
//        String wexinState = request.getParameter("state");
        String openId;
        Token accessToken = weixin.getPageAccessToken(AccessToken::new).getValue();
        if (accessToken.getStatus().

                equals(AccessToken.STATUS_REFRESH_TOKEN_REQUIRED))

        {
            //当前的accessToken是可用的，不用从新获取Token，直接刷新就可可以
            openId = accessToken.getOpenid();
            model.addAttribute("openid", openId);
            model.addAttribute("function", "refresh");
            return "/weixin";
        } else

        {
            if (weixinCode == null || "".equals(weixinCode)) {
                //需要通过网页授权从新获取token
                String redirectUrl = "http://mall.efeiyi.com/application/getOpenidResult";
                String scope = Token.PAGE_SCOPE_SNSAPI_BASE;
                String state = "123";
                String url = weixin.getPageAccessTokenUrl(redirectUrl, scope, state).getValue();
                System.out.println("获取code的请求链接地址结果 URL = " + url);
                return "redirect:" + url;
            } else {
                System.out.println("weixinCode : " + weixinCode);
                accessToken = weixin.getPageAccessToken(weixinCode).getValue();
                openId = accessToken.getOpenid();
                model.addAttribute("openid", openId);
                model.addAttribute("function", "new");
                return "/weixin";
            }
        }
    }

    public AccessToken getCurrenAccessToken() {
        return null;
    }

    @RequestMapping({"/getCurrentAccessTokenStatus"})
    @ResponseBody
    public String getCurrentAccessTokenStatus() {
        Gson gson = new GsonBuilder().create();
        Token accessToken = AccessTokenHolder.getPageAccessToken(AccessToken::new);
        return gson.toJson(accessToken);
    }


    @RequestMapping({"/getWeixinCode"})
    public String getWeixinCode(HttpServletRequest request) throws Exception {
        String redirectUrl = "http://mall.efeiyi.com/application/weixinCodeResult";
        String scope = Token.PAGE_SCOPE_SNSAPI_BASE;
        String state = "123";
        String url = weixin.getPageAccessTokenUrl(redirectUrl, scope, state).getValue();
        System.out.println("获取code的请求链接地址结果 URL = " + url);
        return "redirect:" + url;

    }

    @RequestMapping({"/weixinCodeResult"})
    @ResponseBody
    public String weixinCodeResult(HttpServletRequest request) throws Exception {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        System.out.println("获取code的请求结果： code = " + code);
//        AccessToken accessToken = weixin.getPageAccessToken(code);
//        System.out.println(accessToken);
        return code;
    }


    @RequestMapping({"/getPageAccessToken"})
    @ResponseBody
    public String getPageAccessToken(HttpServletRequest request) throws Exception {
        String code = request.getParameter("code");
        Token accessToken = weixin.getPageAccessToken(code).getValue();
        System.out.println(accessToken);
        return accessToken.toString();
    }

}
