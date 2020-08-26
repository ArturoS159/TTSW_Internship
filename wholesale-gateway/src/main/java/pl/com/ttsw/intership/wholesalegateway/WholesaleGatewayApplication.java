package pl.com.ttsw.intership.wholesalegateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class WholesaleGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WholesaleGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRoute(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(r -> r.path("/soap-service/**")
						.uri("http://localhost:8081/")
						.id("SoapService"))
				.route(r -> r.path("/rest-service/**")
						.uri("http://localhost:8082/")
						.id("RestService"))
				.build();
	}
}
