package com.danozzo.paymybuddy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	      auth.inMemoryAuthentication()
	         .withUser("springuser").password(passwordEncoder().encode("spring123")).roles("USER")
	         .and()
	         .withUser("springadmin").password(passwordEncoder().encode("admin123")).roles("ADMIN", "USER");
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
	      http
	         .authorizeRequests()
	         .antMatchers("/admin").hasRole("ADMIN")
	         .antMatchers("/user").hasRole("USER")
	         .anyRequest().authenticated()
	         .and()
	         .formLogin()
	         .and()
	         .oauth2Login();
	}
	
	@Bean
	 PasswordEncoder passwordEncoder() {
	   return new BCryptPasswordEncoder();
	}

}
