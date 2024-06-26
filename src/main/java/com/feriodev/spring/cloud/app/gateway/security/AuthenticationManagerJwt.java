package com.feriodev.spring.cloud.app.gateway.security;

import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationManagerJwt implements ReactiveAuthenticationManager{

	@Autowired
	private Environment env;
	
	@SuppressWarnings({"deprecation", "unchecked"})
	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		return Mono.just(authentication.getCredentials().toString())
				.map(token -> {
					String keyJwt = env.getProperty("config.security.oauth.jwt.key");
					//byte[] decodedKey = Base64.getDecoder().decode(keyJwt);
					//SecretKey key = Keys.hmacShaKeyFor(decodedKey);
					
					SecretKey key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(keyJwt.getBytes()));					
					return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
				})
				.map(claims ->{
					String username = claims.get("user_name", String.class);					
					List<String> roles = claims.get("authorities", List.class);
					Collection<GrantedAuthority> authorities = roles.stream()
							.map(SimpleGrantedAuthority::new)
							.collect(Collectors.toList());					
					return new UsernamePasswordAuthenticationToken(username, null, authorities);
				});
	}

	
}
