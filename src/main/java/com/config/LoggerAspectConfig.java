package com.config;

import com.logger.ServiceLoggerAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by pgwt on 2017/3/26.
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LoggerAspectConfig {

    @Bean
    public ServiceLoggerAspect serviceLoggerAspect() {
        return new ServiceLoggerAspect();
    }

}
