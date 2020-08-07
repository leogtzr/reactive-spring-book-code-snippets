package com.endpoints;

import com.endpoints.config.AuthenticatedConfiguration;
import com.endpoints.config.ClientProperties;
import com.endpoints.config.DefaultConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = { AuthenticatedConfiguration.class, DefaultConfiguration.class,
		ClientProperties.class, ExerciseEndpointsApplication.class }, //
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, //
		properties = { "spring.profiles.active=client", "server.port=8080",
				"spring.main.web-application-type=reactive" })
class ExerciseEndpointsApplicationTests {

	@Test
	void contextLoads() {
	}

}
