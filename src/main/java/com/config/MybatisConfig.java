package com.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.io.IOException;

@Configuration
public class MybatisConfig {

    private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1/application_test?autoReconnect=true&useUnicode=true&characterEncoding=utf8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123";
    private static final Integer MAX_ACTIVE = 60;
    private static final Integer MAX_IDLE = 10;
    private static final Integer MAX_WAIT = 2000;


    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.application.dao");
        mapperScannerConfigurer.setSqlSessionTemplateBeanName("sqlSession");
        return mapperScannerConfigurer;
    }

    @Bean(name = "sqlSessionFactory")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources("classpath*:config/mapper/*.xml"));
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("config/mybatis-config.xml"));
        return sqlSessionFactoryBean;
    }

    @Bean(name = "sqlSession")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactoryBean().getObject());
    }

    @Bean(name = "transactionManager")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    @Bean(name = "dataSource", destroyMethod = "close")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DataSource dataSource() {
        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setDriverClassName(DRIVER_CLASS_NAME);
        poolProperties.setUrl(URL);
        poolProperties.setUsername(USERNAME);
        poolProperties.setPassword(PASSWORD);
        poolProperties.setValidationQuery("SELECT 1");
        poolProperties.setTestWhileIdle(true);
        poolProperties.setTimeBetweenEvictionRunsMillis(3600000);
        poolProperties.setMinEvictableIdleTimeMillis(18000000);
        poolProperties.setTestOnBorrow(true);
        poolProperties.setMaxActive(MAX_ACTIVE);
        poolProperties.setMaxIdle(MAX_IDLE);
        poolProperties.setMaxWait(MAX_WAIT);
        poolProperties.setDefaultAutoCommit(true);
        poolProperties.setRemoveAbandoned(true);
        poolProperties.setRemoveAbandonedTimeout(60);
        DataSource dataSource = new DataSource();
        dataSource.setPoolProperties(poolProperties);
        return dataSource;

    }


}
