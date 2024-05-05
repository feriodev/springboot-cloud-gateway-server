package com.feriodev.spring.cloud.app.gateway.factory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfigGatewayFilterFactory {
	
	private String message;
	private String cookieValue;
	private String cookieName;
	private String token;
}
