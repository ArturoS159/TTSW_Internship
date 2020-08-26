package pl.com.ttsw.intership.wholesaleserverapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class WholesaleServerAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(WholesaleServerAppApplication.class, args);
    }
}
