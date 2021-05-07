package com.olx.zuul.server.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.olx.zuul.server.security.JwtConfig;

@EnableWebSecurity 	
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JwtConfig jwtConfig;
 
	@Override
  	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors()
		.and()
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) 	
		.and()
		.exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED)) 	
		.and()
		.addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
		.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/userservice/register").permitAll()  
		.antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()  
		.antMatchers(HttpMethod.POST, "/logservice/logs").hasAnyRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/userservice/all").hasAnyRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/**").hasAnyRole("ADMIN","USER")
		.antMatchers(HttpMethod.POST, "/**").hasAnyRole("ADMIN","USER")
		.antMatchers(HttpMethod.PUT, "/**").hasAnyRole("ADMIN","USER")
		.antMatchers(HttpMethod.DELETE, "/**").hasAnyRole("ADMIN","USER")
		.anyRequest().authenticated(); 
	}
	
	@Bean
  	public JwtConfig jwtConfig() {
    	   return new JwtConfig();
  	}
}