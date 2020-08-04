package com.customer.filter;

import com.customer.config.LowercaseWebConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest({LowercaseWebConfiguration.class, LowercaseWebFilter.class})
public class LowercaseWebFilterTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void greet() throws Exception {
        test("/hi/jane", "jane");
        test("/HI/jane", "jane");
    }

    void test(final String path, String match) {
        this.client
                .get()
                .uri("http://localhost:8080/" + path)
                .exchange().expectStatus().isOk()
                // text/plain;charset=UTF-8
                // .expectHeader().contentType(MediaType.TEXT_PLAIN)
                .expectBody(String.class).value(message -> message.equalsIgnoreCase(String.format("Hello, %s!", match)))
                ;
    }

}
