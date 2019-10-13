package com.sbt.stellar.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.sbt.stellar.repositories", entityManagerFactoryRef = "emf")
public class HibernateConfiguration {
    @Value("${db.driver}")
    private String DRIVER;

    @Value("${db.password}")
    private String PASSWORD;

    @Value("${db.url}")
    private String URL;

    @Value("${db.username}")
    private String USERNAME;

    @Value("${hibernate.dialect}")
    private String DIALECT;

    @Value("${hibernate.hbm2ddl.auto}")
    private String HBM2DDL_AUTO;

    @Value("${entitymanager.packagesToScan}")
    private String PACKAGES_TO_SCAN;



    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }

    @Bean("emf")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(PACKAGES_TO_SCAN);
        Properties hibernateProperties = additionalProperties();
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

    Properties additionalProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.hbm2ddl.auto", HBM2DDL_AUTO);
        hibernateProperties.put("hibernate.dialect", DIALECT);
        /*hibernateProperties.put("hibernate.show_sql", SHOW_SQL);
        hibernateProperties.put("hibernate.naming-strategy", NAMING_STRATEGY);
        hibernateProperties.put("hibernate.globally_quoted_identifiers", GLOBALLY_QUOTED_IDENTIFIERS);
        hibernateProperties.put("hibernate.format_sql", FORMAT_SQL);
        hibernateProperties.put("hibernate.temp.use_jdbc_metadata_defaults", TEMP_USE_JDBC);
        */return hibernateProperties;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }


}
