package com.getjavajob.training.timashovy.socialnetwork.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.group")
public class PersistenceConfig {

//    @Bean
//    public DataSource dataSource() {
//        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
//        jndiObjectFactoryBean.setJndiName("java:/comp/env/jdbc/socialNetwork");
//        jndiObjectFactoryBean.setResourceRef(true);
//        jndiObjectFactoryBean.setExpectedType(javax.sql.DataSource.class);
//        jndiObjectFactoryBean.setProxyInterface(DataSource.class);
//        try {
//            // Необходимо для правильной инициализации
//            jndiObjectFactoryBean.afterPropertiesSet();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to initialize JNDI DataSource", e);
//        }
//        Object object = jndiObjectFactoryBean.getObject();
//        if (object instanceof DataSource) {
//            return (DataSource) object;
//        } else {
//            throw new RuntimeException("JNDI resource is not a DataSource");
//        }
//    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/social_network");
        dataSource.setUsername("postgres");
        dataSource.setPassword("21122012loliwe+");
        return dataSource;}


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan("com.getjavajob.training.timashovy.socialnetwork.domain");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQL95Dialect");
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        // Properties jpaProperties = new Properties();
        // jpaProperties.put("hibernate.format_sql", true);
        // jpaProperties.put("hibernate.use_sql_comments", true);
        // em.setJpaProperties(jpaProperties);
        return entityManagerFactory;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
