package com.onboarding.pos.spring.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*
** config data source , entity manager here
*/

@Configuration
@EnableJpaRepositories( basePackages = {"com.onboarding.pos.spring.repository" })
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.onboarding.pos.spring.service", "com.onboarding.pos.spring.repository", "com.onboarding.pos.spring.controller"})
public class BasicConfig {
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/modelpos?autoReconnect=true");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres");

		return dataSource;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.POSTGRESQL);
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setShowSql(false);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(getClass().getPackage().getName());
		factory.setDataSource(dataSource());
		factory.setJpaProperties(jpaProperties());

		return factory;
	}
	
	private Properties jpaProperties() {  
	        Properties properties = new Properties();  
	        
	        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");  // MySQL5InnoDBDialect   MySQLMyISAMDialect
	        properties.put("hibernate.show_sql", "false");  
	        return properties;  
	}  
	@Bean
	public PlatformTransactionManager transactionManager() {

		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return txManager;
	}
	
	
}