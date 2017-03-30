package com.application.config;

import com.application.web.service.impl.UserServiceImpl;
import com.application.security.auth.IsAuthenticatedSecurityStrategy;
import com.application.weixin.Weixin;
import com.application.weixin.service.TokenService;
import com.application.weixin.service.impl.TokenServiceImpl;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.application.web.dao", "com.application.web.service.impl"}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)
})
public class RootConfig {

    @Bean(name = "securityStrategy")
    public IsAuthenticatedSecurityStrategy isAuthenticatedSecurityStrategy(UserServiceImpl userService) {
        List<String> urlList = new ArrayList<>();
        urlList.add("/order/**");
        IsAuthenticatedSecurityStrategy securityStrategy = new IsAuthenticatedSecurityStrategy(userService, "/login", urlList);
        return securityStrategy;
    }

    @Bean(name = "tokenService")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public TokenService tokenService() {
        return new TokenServiceImpl();
    }

    @Bean(name = "weixin")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Weixin weixin() throws Exception {
        Weixin weixin = new Weixin("wx7f6aa253b75466dd", "04928de13ab23dca159d235ba6dc19ea", Weixin.TYPE_FUWUHAO);
        weixin.setTokenService(tokenService());
        return weixin;
    }

}
