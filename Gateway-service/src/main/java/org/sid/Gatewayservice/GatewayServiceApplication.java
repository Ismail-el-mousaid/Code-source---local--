package org.sid.Gatewayservice;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.client.WebClient;


@SpringBootApplication
//@CrossOrigin("*")
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}


	/*--------Configuration statique------------*/
	/*	@Bean
	RouteLocator routeLocator(RouteLocatorBuilder builder){
		return builder.routes()
				.route((r)->r.path("/deploiements/**").uri("lb://MS-MODULES")) //On n'a plus besoin de connaitre @ du micro-service (seulement le nom) car est déjà enregisté dans Discovery
				.route((r)->r.path("/modules/**").uri("http://localhost:8081/"))
				.build();
	}  */


	/*----------Configuration dynamique-----------*/
	@Bean
	DiscoveryClientRouteDefinitionLocator definitionLocator(
			ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties properties){
		return new DiscoveryClientRouteDefinitionLocator(rdc, properties);
	}



}

