package pl.com.ttsw.intership.soapservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class WaresSoapServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaresSoapServiceApplication.class, args);
	}

}
