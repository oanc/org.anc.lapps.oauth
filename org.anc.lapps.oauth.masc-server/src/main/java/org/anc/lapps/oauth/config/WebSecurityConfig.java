package org.anc.lapps.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

/**
 * @author Keith Suderman
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
//	@Value("${user.database.driver}")
//	private String databaseDriver;
//	@Value("${user.database.url}")
//	private String databaseUrl;
//	@Value("${user.database.username")
//	private String databaseUsername;
//	@Value("${user.database.password")
//	private String databasePassword;

	@Autowired
	protected SecurityProperties security;

	@Autowired
	protected DataSource userDataSource;

	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests()
				  .antMatchers("/test/**", "/css/**", "/fonts/**").permitAll()
				  .antMatchers("/masc/**").hasRole("USER")
				  .anyRequest().hasRole("ADMIN")
				  .and()
				  .formLogin()
				      .loginPage("/login")
				      .failureUrl("/login?error=1")
				      .permitAll()
				  .and()
				  .logout()
				      .logoutUrl("/logout")
				      .permitAll();
//		http.authorizeRequests()
//				  .antMatchers("/test").permitAll()
//				  .anyRequest().authenticated()
//			.and()
//				  .formLogin()
//				  		.loginPage("/login")
//				  		.permitAll()
//			.and()
//				  .logout()
//				  .permitAll();

//		http.authorizeRequests().anyRequest().fullyAuthenticated()
//				  .and()
//				  .formLogin().loginPage("/login").failureUrl("/login?error").permitAll();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.jdbcAuthentication().dataSource(userDataSource);

//		auth.inMemoryAuthentication()
//				  .withUser("admin")
//				      .password("password")
//				      .roles("ADMIN", "USER")
//				  .and()
//				  .withUser("user")
//				      .password("secret")
//				  		.roles("USER");
	}

}
