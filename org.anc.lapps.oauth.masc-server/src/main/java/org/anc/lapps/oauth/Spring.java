package org.anc.lapps.oauth;

import org.anc.thymeleaf.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

/**
 * @author Keith Suderman
 */
@Configuration
@ComponentScan
@EnableJpaRepositories
@EnableAutoConfiguration
public class Spring extends SpringBootServletInitializer
{
	private static ConfigurableApplicationContext context;

	@Value("${user.datasource.url}")
	protected String userDataSourceUrl;
	@Value("${user.datasource.driver}")
	protected String userDataSourceDriver;
	@Value("${user.datasource.username}")
	protected String userDataSourceUsername;
	@Value("${user.datasource.password}")
	protected String userDataSourcePassword;

	@Bean
	public ButtonDialect getButtonDialect() { return new ButtonDialect(); }
	@Bean
	public PanelDialect getPanelDialect() { return new PanelDialect(); }
	@Bean
	public MenuDialect getMenuDialect() { return new MenuDialect(); }
	@Bean
	public WordpressDialect getWordpressDialect() { return new WordpressDialect(); }
	@Bean
	public IconDialect getIconDialect() { return new IconDialect(); }

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource primaryDataSource()
	{
		return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties(prefix = "user.datasource")
	public DataSource userDataSource()
	{
		System.out.println("*******************************");
		System.out.println("***** Creating user datasource at " + userDataSourceUrl);
		System.out.println("*******************************");
		return DataSourceBuilder.create().build();
//		return DataSourceBuilder.create()
//				  .username(userDataSourceUrl)
//				  .driverClassName(userDataSourceDriver)
//				  .username(userDataSourceUsername)
//				  .password(userDataSourcePassword)
//				  .build();
	}

	public Spring()
	{

	}

	@Override
	public SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		return application.showBanner(false).sources(Spring.class);
	}

	public static void main(String... args)
	{
		System.out.println("Starting Spring application.");
		context = SpringApplication.run(Spring.class, args);
	}

	public static <T> T getBean(Class<T> beanClass)
	{
		return context.getBean(beanClass);
	}
}
