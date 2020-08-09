package com.customer.consumer;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RunWith(SpringRunner.class)
@Import(CustomerConsumerApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
public class WiremockCustomerClientTest {

    @Autowired
    private CustomerClient client;

    @Autowired
    private Environment environment;

    @Before
    public void setupWireMock() {
        final String base =
                String.format("%s:%s", "localhost", this.environment.getProperty("wiremock.server.port", Integer.class));
        this.client.setBase(base);

        final String json = "[{ \"id\":\"1\", \"name\":\"Jane\"},"
                + "{ \"id\":\"2\", \"name\":\"John\"}]";

        WireMock.stubFor(
            WireMock.get("/customers")
                .willReturn(WireMock.aResponse()
                .withHeader(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
                        .withBody(json)
                        )
        );
    }

    @Test
    public void getAllCustomers() {
        final Flux<Customer> customers = this.client.getAllCustomers();
        StepVerifier
                .create(customers)
                .expectNext(new Customer("1", "Jane"))
                .expectNext(new Customer("2", "John"))
                .verifyComplete()
                ;
    }

}
