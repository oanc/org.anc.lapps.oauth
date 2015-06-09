package org.anc.lapps.oauth;

import org.anc.thymeleaf.ButtonDialect;
import org.anc.thymeleaf.PanelDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
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
	@Bean
	public ButtonDialect getButtonDialect() { return new ButtonDialect(); }
	@Bean
	public PanelDialect getPanelDialect() { return new PanelDialect(); }

	public Spring()
	{

	}

	@Override
	public SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		return application.sources(Spring.class);
	}

	public static void main(String[] args)
	{
		SpringApplication.run(Spring.class, args);
	}
}
