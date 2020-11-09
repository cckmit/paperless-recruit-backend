package com.xiaohuashifu.recruit.authentication.service.configuration;

import com.xiaohuashifu.recruit.authentication.service.filter.OptionsRequestFilter;
import com.xiaohuashifu.recruit.authentication.service.service.JwtAuthenticationProvider;
import com.xiaohuashifu.recruit.authentication.service.service.JwtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	private final UserDetailsService userDetailsService;

	public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		        .antMatchers("/image/**").permitAll()
		        .antMatchers("/admin/**").hasAnyRole("ADMIN")
		        .antMatchers("/article/**").hasRole("USER")
		        .anyRequest().authenticated()
		        .and()
		    .csrf().disable()
		    .formLogin().disable()
		    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
		    .cors()
		    .and()
		    .headers().addHeaderWriter(new StaticHeadersWriter(Arrays.asList(
		    		new Header("Access-control-Allow-Origin","*"),
		    		new Header("Access-Control-Expose-Headers","Authorization"))))
		    .and()
		    .addFilterAfter(new OptionsRequestFilter(), CorsFilter.class)
		    .apply(new JsonLoginConfigurer<>()).loginSuccessHandler(jsonLoginSuccessHandler())
		    .and()
		    .apply(new JwtLoginConfigurer<>()).tokenValidSuccessHandler(jwtRefreshSuccessHandler()).permissiveRequestUrls("/logout")
		    .and()
		    .logout()
//		        .logoutUrl("/logout")   //默认就是"/logout"
		        .addLogoutHandler(tokenClearLogoutHandler())
		        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
				.and().userDetailsService(userDetailsService);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider()).authenticationProvider(jwtAuthenticationProvider());
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}
	
	@Bean("jwtAuthenticationProvider")
	protected AuthenticationProvider jwtAuthenticationProvider() {
		return new JwtAuthenticationProvider((JwtUserService) userDetailsService);
	}
	
	@Bean("daoAuthenticationProvider")
	protected AuthenticationProvider daoAuthenticationProvider() throws Exception{
		//这里会默认使用BCryptPasswordEncoder比对加密后的密码，注意要跟createUser时保持一致
		DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
		daoProvider.setUserDetailsService(userDetailsService());
		return daoProvider;
	}
	
	@Bean
	protected JsonLoginSuccessHandler jsonLoginSuccessHandler() {
		return new JsonLoginSuccessHandler((JwtUserService) userDetailsService);
	}
	
	@Bean
	protected JwtRefreshSuccessHandler jwtRefreshSuccessHandler() {
		return new JwtRefreshSuccessHandler((JwtUserService) userDetailsService);
	}
	
	@Bean
	protected TokenClearLogoutHandler tokenClearLogoutHandler() {
		return new TokenClearLogoutHandler((JwtUserService) userDetailsService);
	}
	
	@Bean
	protected CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST","HEAD", "OPTION"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.addExposedHeader("Authorization");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}



}
