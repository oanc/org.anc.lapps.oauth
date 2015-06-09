package org.anc.lapps.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * @author Keith Suderman
 */
@SpringBootApplication
public class Spring extends SpringBootServletInitializer
{
//	static {
//		try
//		{
//			Class.forName("org.h2.Driver");
//		}
//		catch (ClassNotFoundException e)
//		{
//			e.printStackTrace();
//		}
//	}

	@Override
	public SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		return application.sources(Spring.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Spring.class, args);
	}

}