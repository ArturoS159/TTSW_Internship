package pl.com.ttsw.intership.wholesaleconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@EnableConfigServer
public class WholesaleConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(WholesaleConfigApplication.class, args);
	}

}
