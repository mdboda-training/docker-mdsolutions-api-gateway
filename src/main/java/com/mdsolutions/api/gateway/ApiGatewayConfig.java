package com.mdsolutions.api.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {

		return builder.routes()
				.route(p -> p.path("/get")
						.filters(f -> f.addRequestHeader("myHeader", "myTestingHeader")
								.addRequestParameter("myReqParam", "myTestingRequestingParam"))
						.uri("http://httpbin.org:80"))
				.route(p -> p.path("/docker-mdsolutions-rest-service-provider/**")
						.uri("lb://docker-mdsolutions-rest-service-provider/"))
				.route(p -> p.path("/docker-mdsolutions-feign-client/**").uri("lb://docker-mdsolutions-feign-client/"))
				.route(p -> p.path("/docker-mdsolutions-loadusers/**")
						.filters(f -> f.rewritePath("/docker-mdsolutions-loadusers/(?<segment>.*)",
								"/docker-mdsolutions-feign-client/${segment}"))

						.uri("lb://mdsolutions-feign-client/"))
				.build();
	}

}
