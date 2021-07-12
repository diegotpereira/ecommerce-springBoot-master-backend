package br.com.java.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
//@EnableTransactionManagement
public class DatabaseConfig {

	
	@Value("${spring.datasource.url}")
	private String DB_URL;
	
	@Value("${spring.datasource.username}")
	private String DB_USERNAME;
	
	@Value("${spring.datasource.password}")
	private String DB_PASSWORD;
	
	@Value("${spring.datasource.driverClassName}")
	private String DB_DRIVER;
	
	
	
	@Bean
	  public DataSource dataSource() {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(DB_DRIVER);
	    dataSource.setUrl(DB_URL);
	    dataSource.setUsername(DB_USERNAME);
	    dataSource.setPassword(DB_PASSWORD);
	    return dataSource;
	  }
}
