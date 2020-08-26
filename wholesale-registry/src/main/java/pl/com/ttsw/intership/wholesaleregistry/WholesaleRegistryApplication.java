package pl.com.ttsw.intership.wholesaleregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class WholesaleRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(WholesaleRegistryApplication.class, args);
	}

}
