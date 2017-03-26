package com.config;


import com.weixin.Weixin;
import com.weixin.service.TokenService;
import com.weixin.service.impl.TokenServiceImpl;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.ejb.Schedule;
import java.util.ArrayList;
import java.util.List;


@Configuration
@ComponentScan(basePackages = {"com.**.**.controller", "com.**.**.dao", "com.**.**.service.impl"})
public class SpringMVCConfig {

    @Bean(name = "multipartResolver")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public CommonsMultipartResolver commonsMultipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(2_000_000L);
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        return commonsMultipartResolver;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter handlerAdapter = new RequestMappingHandlerAdapter();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(mappingJackson2HttpMessageConverter());
        handlerAdapter.setMessageConverters(messageConverters);
        return handlerAdapter;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        messageConverter.setSupportedMediaTypes(supportedMediaTypes);
        return messageConverter;
    }

    @Bean(name = "viewResolver")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
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
