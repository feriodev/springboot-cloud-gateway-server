package com.feriodev.spring.cloud.app.gateway.factory;

import java.util.Arrays;
import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomGatewayFilterFactory extends AbstractGatewayFilterFactory<ConfigGatewayFilterFactory>  {

	public CustomGatewayFilterFactory() {
		super(ConfigGatewayFilterFactory.class);
	}	
	
	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList("message", "cookieName", "cookieValue");
	}
	
	@Override
	public String name() {
		return "Customizable";
	}
	
	@Override
	public GatewayFilter apply(ConfigGatewayFilterFactory config) {
		return new OrderedGatewayFilter((exchange, chain) -> {
			log.info("Ejecutando pre-filterFactory: " + config.getMessage());

			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				log.info("Ejecutando post-filterFactory: " + config.getMessage());
				
				/*Optional.ofNullable(config.getCookieValue()).ifPresent(cookie -> {
					exchange.getResponse()
							.addCookie(ResponseCookie.from(config.getCookieName(), config.getCookieValue()).build());
				});*/
			}));
		}, 2);
	}

}
