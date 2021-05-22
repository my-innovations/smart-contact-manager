package com.contactmanager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	/*public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
	}*/
	
	//Ignoring the static resources.
	/*@Override
	public void configure(WebSecurity web)throws Exception{
		web.ignoring().antMatchers( "/css/**","/icons/**","/images/**","/js/**","/layer/**","/webjars/**");
	}*/
	
	//OR
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//Ignoring the static resources.
		String[] resources = new String[]{
                "/", "/home","/pictureCheckCode","/include/**",
                "/css/**","/icons/**","/images/**","/js/**","/layer/**","/webjars/**"
        };
		
		http
		.csrf().disable()
		.headers().frameOptions().disable()
		.and()
		.authorizeRequests()
		.antMatchers(resources).permitAll()
		.antMatchers("/","/home","/h2-console/**","/reg","/signin").permitAll()
		.antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/user/**").hasRole("USER")
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/signin")
		.loginProcessingUrl("/doLogin")
		.defaultSuccessUrl("/user/dashboard");
	}
	
	//###################################################################################################################

	//way-1 : Gathering user deatils from properties file and authenticating user.
	//way-2 : Gathering user deatils and authenticating user through in memory authentication.
	/*@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("punya").password("punya").roles("USER");
	}*/
	
	//OR
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
	}
	
	@Bean
	PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
