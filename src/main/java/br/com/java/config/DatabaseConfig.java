package br.com.java.config;


import javax.sql.DataSource;

import com.zaxxer.hikari.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DatabaseConfig {
	
	  @Autowired
	  private Environment env;

	  @Autowired
	  private DataSource dataSource;
	
	 @Bean
	  public DataSource dataSource() {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(env.getProperty("db.driver"));
	    dataSource.setUrl(env.getProperty("db.url"));
	    dataSource.setUsername(env.getProperty("db.username"));
	    dataSource.setPassword(env.getProperty("db.password"));
	    return dataSource;
	  }
}
