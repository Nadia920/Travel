package com.java.Travel.config;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.hibernate.hql.internal.ast.tree.IsNullLogicOperatorNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Configuration
@ComponentScan(basePackages = "com.java.Travel")
@EnableTransactionManagement
@PropertySource("classpath:application.yml")
public class HibernateConfig {

    @Value("${spring.datasource.platform}")
    private String platform;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;


   private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("spring.datasource.platform", platform);
        /*properties.setProperty("spring.datasource.url", url));
        properties.setProperty("spring.datasource.username", username));
        properties.setProperty("spring.datasource.password", password));*/
       System.out.println("\n\n\n\n\n" + "from 1" + "\n\n\n\n\n");
        return properties;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        System.out.println("\n\n\n\n\n" + "from 2" + "\n\n\n\n\n");
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name="entityManagerFactory")
    public LocalSessionFactoryBean sessionFactory() {
        System.out.println("\n\n\n\n\n" + "from 3.1" + "\n\n\n\n\n");
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        System.out.println("\n\n\n\n\n" + "from 3" + "\n\n\n\n\n");
        System.out.println("\n\n\n\n\n" + sessionFactory + "\n\n\n\n\n");
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.java.Travel.model");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

}
