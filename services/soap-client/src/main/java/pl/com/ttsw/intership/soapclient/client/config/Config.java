package pl.com.ttsw.intership.soapclient.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import pl.com.ttsw.intership.soapclient.client.connector.*;

@Configuration
public class Config {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("pl.com.ttsw.intership.waressoapservice.models");
        return marshaller;
    }

    @Bean
    public SOAPConnector soapConnector(Jaxb2Marshaller marshaller) {
        SOAPConnector client = new SOAPConnector();
        client.setDefaultUri("http://localhost:8080/soap-service");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
