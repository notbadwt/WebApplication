package com.application.config;

import com.application.util.DataSourceAspect;
import com.application.util.DynamicDataSource;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MybatisConfig {

    private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private static final Integer MAX_ACTIVE = 60;
    private static final Integer MAX_IDLE = 10;
    private static final Integer MAX_WAIT = 2000;

    private static final String MASTER_URL = "jdbc:mysql://127.0.0.1/application_test?autoReconnect=true&useUnicode=true&characterEncoding=utf8";
    private static final String MASTER_USERNAME = "root";
    private static final String MASTER_PASSWORD = "1234";

    private static final String SLAVE_1_URL = "jdbc:mysql://127.0.0.1/application_test?autoReconnect=true&useUnicode=true&characterEncoding=utf8";
    private static final String SLAVE_1_USERNAME = "root";
    private static final String SLAVE_1_PASSWORD = "1234";

    private static final String SLAVE_2_URL = "jdbc:mysql://127.0.0.1/application_test?autoReconnect=true&useUnicode=true&characterEncoding=utf8";
    private static final String SLAVE_2_USERNAME = "root";
    private static final String SLAVE_2_PASSWORD = "1234";


    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.application.web.dao");
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

    @Bean(name = "dataSource-master", destroyMethod = "close")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DataSource masterDataSource() {
        PoolProperties poolProperties = createDataSourceProperties(MASTER_URL, MASTER_USERNAME, MASTER_PASSWORD);
        DataSource dataSource = new DataSource();
        dataSource.setPoolProperties(poolProperties);
        return dataSource;

    }

    @Bean(name = "dataSource-slave1", destroyMethod = "close")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DataSource slave1DataSource() {
        PoolProperties poolProperties = createDataSourceProperties(SLAVE_1_URL, SLAVE_1_USERNAME, SLAVE_1_PASSWORD);
        DataSource dataSource = new DataSource();
        dataSource.setPoolProperties(poolProperties);
        return dataSource;
    }

    @Bean(name = "dataSource-slave2", destroyMethod = "close")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DataSource slave2DataSource() {
        PoolProperties poolProperties = createDataSourceProperties(SLAVE_2_URL, SLAVE_2_USERNAME, SLAVE_2_PASSWORD);
        DataSource dataSource = new DataSource();
        dataSource.setPoolProperties(poolProperties);
        return dataSource;
    }


    private PoolProperties createDataSourceProperties(String url, String username, String password) {
        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setDriverClassName(DRIVER_CLASS_NAME);
        poolProperties.setUrl(url);
        poolProperties.setUsername(username);
        poolProperties.setPassword(password);
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
        return poolProperties;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DataSourceAspect dataSourceAspect() {
        return new DataSourceAspect();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DynamicDataSource dataSource() {
        DynamicDataSource dataSource = new DynamicDataSource();
        List<DataSource> slaves = new ArrayList<>();
        slaves.add(slave1DataSource());
        slaves.add(slave2DataSource());
        dataSource.setSlaves(slaves);
        dataSource.setMaster(masterDataSource());
        dataSource.afterPropertiesSet();
        return dataSource;
    }


}
