package com.lee.tac;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.dubbo.remoting.http.servlet.DispatcherServlet;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
// 初始化dubbo
@ImportResource("classpath:dubbo.xml")
// 声明要扫描的Mybatis-Mapper的package，会扫描该package下的所有接口
@MapperScan("com.lee.tac.mapper")
public class AppConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.tac")
    public DataSource dataSource() {
        // 使用内嵌的hsqldb作为DEMO的测试
        // return new EmbeddedDatabaseBuilder().addScript("schema.sql").build();
        // 日常使用spring-data数据源或者其他开源的数据库连接池
        // return DataSourceBuilder.create().build();
        return new org.apache.tomcat.jdbc.pool.DataSource();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 声明mapper配置文件路径
        sessionFactory.setMapperLocations(resolver.getResources("classpath:/mybatis/*.xml"));
        return sessionFactory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public ServletRegistrationBean servlet() {
        // 使用servlet bridge暴露dubbo http
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
                new DispatcherServlet(), "/services/*");
        // 必须指定别名，spring boot内置的已经使用了dispatcherServlet
        servletRegistrationBean.setName("dubbo");
        return servletRegistrationBean;
    }
}
