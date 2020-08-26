package pl.com.ttsw.intership.wholesaleconfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WholesaleConfigApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	void defaultConfigurationAvailable() {
	}

}
