package com.feriodev.spring.cloud.app.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@Configuration
public class SpringSecurityConfig {
	
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	
	@Bean
	SecurityWebFilterChain configure(ServerHttpSecurity http) {		
		return http.authorizeExchange()
				.pathMatchers("/api/security/oauth/**").permitAll()
				.pathMatchers(HttpMethod.GET, 
						"/api/product/list", 
						"/api/client/list", 
						"/api/user/security",
						"/api/client/detail/{id}/count/{count}",
						"/api/product/detail/{id}").permitAll()
				.pathMatchers(HttpMethod.GET,
						"/api/user/security/{id}").hasAnyRole("ADMIN", "ROLE")
				.pathMatchers(HttpMethod.POST,
						"/api/product/**",
						"/api/client/**",
						"/api/user/security/**").hasRole("ADMIN")
				.anyExchange().authenticated()
				.and()
				.addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
				.csrf().disable()
				.build();
	}
}
