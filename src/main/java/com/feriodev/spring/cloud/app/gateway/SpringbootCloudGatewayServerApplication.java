package com.feriodev.spring.cloud.app.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringbootCloudGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCloudGatewayServerApplication.class, args);
	}
}
