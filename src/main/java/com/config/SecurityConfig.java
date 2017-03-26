package com.config;

import com.application.service.impl.UserServiceImpl;
import com.security.auth.IsAuthenticatedSecurityStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public IsAuthenticatedSecurityStrategy isAuthenticatedSecurityStrategy(UserServiceImpl userService) {
        List<String> urlList = new ArrayList<>();
        urlList.add("/order/**");
        IsAuthenticatedSecurityStrategy securityStrategy = new IsAuthenticatedSecurityStrategy(userService, "/login", urlList);
        return securityStrategy;
    }

}
