package com.feriodev.spring.cloud.app.gateway.filters;

import java.util.Optional;
import java.util.UUID;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered{

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		log.info("Ejecutando pre-filter");
		String uuid = UUID.randomUUID().toString();
		exchange.getRequest().mutate().headers(h -> h.add("Transaction", uuid));
		
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			
			log.info("Ejecutando post-filter");
			
			Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("Transaction")).ifPresent(valor -> {
				exchange.getResponse().getHeaders().add("Transaction", valor);
			});
			
			exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "rojo").build());
			
		}));
	}

	@Override
	public int getOrder() {
		return 1;
	}

}
