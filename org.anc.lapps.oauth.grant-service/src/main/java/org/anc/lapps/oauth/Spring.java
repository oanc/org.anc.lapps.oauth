package org.anc.lapps.oauth;

import org.anc.thymeleaf.ButtonDialect;
import org.anc.thymeleaf.PanelDialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
//import org.springframework.boot.actuate.autoconfigure.ShellProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Keith Suderman
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Spring extends SpringBootServletInitializer
{
	@Value("${menu.index")
	public String MENU_INDEX;
	@Value("$menu.client.list")
	public String MENU_CLIENT_LIST;
	@Value("$menu.client.register")
	public String MENU_CLIENT_REGISTER;

	@Bean
	ButtonDialect getButtonDialect() { return new ButtonDialect(); }
	@Bean
	PanelDialect getPanelDialect() { return new PanelDialect(); }

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		System.out.println("Configuring the SpringApplicationBuilder.");
		return application.sources(Spring.class);
	}

	public Spring()
	{
	}

	public static void main(String[] args)
	{
		System.out.println("Staring the Pipeline Planner's SpringApplication");
		SpringApplication.run(Spring.class, args);
	}
}
