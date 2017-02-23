package com.application.controller;


import com.application.entity.User;
import com.application.security.auth.AbstractSecurity;
import com.application.util.CommonUtil;
import com.application.weixin.AccessTokenHolder;
import com.application.weixin.Weixin;
import com.application.weixin.model.AccessToken;
import com.application.weixin.model.Result;
import com.application.weixin.model.Token;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

@Controller
public class TestWeixinController {

    @Autowired
    private AbstractSecurity abstractSecurity;

    private Weixin weixin;

    @Autowired
    public TestWeixinController(Weixin weixin) {
        this.weixin = weixin;
    }

    @RequestMapping({"/getOpenidResult"})
    public String getOpenIdResult(HttpServletRequest request, Model model) throws Exception {
        String weixinCode = request.getParameter("code");
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
        } else {
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

    @RequestMapping({"/pageAccess"})
    public String pageAccess(HttpServletRequest request, Model model) {
        //第一步，尝试从缓存当中取出token，
        //第二部，如果token不存在（==null），就使用网页授权的方式，不然直接返回处理微信网页授权的页面

        User currentUser = (User) abstractSecurity.getCurrentUser(request);
        String weixinCode = request.getParameter("code");
        Result<Token> tokenResult = null;
        Gson gson = new GsonBuilder().create();
        if (weixinCode != null && !"".equals(weixinCode)) {
            tokenResult = weixin.setTokenType(AccessToken.class).getPageAccessToken(weixinCode);
            if (tokenResult.getResultStatus().equals(Result.SUCCESS)) {
                System.out.println(gson.toJson(tokenResult.getValue()));
                model.addAttribute("openid", tokenResult.getValue().getOpenid());
                if (currentUser != null) {
                    AccessTokenHolder.cacheToken(currentUser.getId(), tokenResult.getValue());
                }
                return "/weixin";
            }
        }

        if (currentUser != null) {
            tokenResult = weixin.setTokenType(AccessToken.class).getPageAccessToken(() -> AccessTokenHolder.getTokenFromCache(currentUser.getId()));
        }

        if (tokenResult != null && tokenResult.getValue() != null) {
            System.out.println("");
            System.out.println("RefreshToken");
            System.out.println("");
            System.out.println(gson.toJson(tokenResult.getValue()));
            model.addAttribute("openid", tokenResult.getValue().getOpenid());
            if (currentUser != null) {
                AccessTokenHolder.cacheToken(currentUser.getId(), tokenResult.getValue()); //将得到token从新村到缓存当中
            }
            return "/weixin";
        } else {

            System.out.println("");
            System.out.println("NewAccessToken ");
            System.out.println("");

            String redirectUrl = "http://mall.efeiyi.com/application/pageAccess";
            String state = "123";
            Result<String> urlResult = weixin.setTokenType(AccessToken.class).getPageAccessTokenUrl(redirectUrl, Token.PAGE_SCOPE_SNSAPI_BASE, state);
            return "redirect:" + urlResult.getValue();
        }

    }


    public <T> T checkResult(Result<T> result, Supplier<T> successSupplier, Supplier<T> errorSupplier) {
        if (result.getResultStatus().equals(Result.SUCCESS)) {
            return successSupplier.get();
        } else {
            return errorSupplier.get();
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

    @RequestMapping({"/checkToken"})
    public void checkToken(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        boolean isCheckToken = false;

        PrintWriter printWriter = response.getWriter();

        if (signature != null && timestamp != null && nonce != null && echostr != null) {
            String token = "11111111111111111111111111111111";
            List<String> list = Arrays.asList(token, timestamp, nonce);
            Collections.sort(list);
            String temp = "";
            for (String s : list) {
                temp += s;
            }
            String hashcode = CommonUtil.encodePassword(temp, "SHA1");
            System.out.println("hashcode: " + hashcode);
            System.out.println("signature: " + signature);
            System.out.println("echostr: " + echostr);
            if (signature.equals(hashcode)) {
                isCheckToken = true;
            } else {
                isCheckToken = false;
            }
        } else {
            isCheckToken = false;
        }

        if (isCheckToken) {
            printWriter.print(echostr);
            printWriter.flush();
            printWriter.close();
        }



    }

}
