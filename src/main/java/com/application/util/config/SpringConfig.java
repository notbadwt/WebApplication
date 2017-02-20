package com.application.util.config;

import com.application.weixin.model.AccessToken;
import com.application.weixin.model.Token;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean(name = "tokenType")
    public Class<? extends Token> listStrBean() {
        return AccessToken.class;
    }
}