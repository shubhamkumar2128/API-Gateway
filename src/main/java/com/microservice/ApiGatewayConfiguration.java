package com.microservice;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p->p.path("/currecny-exchange/**").uri("lb://currecny-exchange"))
				.route(p->p.path("/currecny-conversion/**").uri("lb://currecny-conversion"))
				.route(p->p.path("/currecny-conversion-feign/**").uri("lb://currecny-exchange-feign"))
				.route(p -> p.path("/currecny-conversion-new/**")
						.filters(f -> f.rewritePath("/currecny-conversion-new/(?<segment>.*)",
								"/currecny-conversion-feign/${segment})"))
						.uri("lb://currecny-conversion"))

				.build();
	}

}
