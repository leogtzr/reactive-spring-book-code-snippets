package com.tdd.web;

import com.tdd.config.CustomerWebConfiguration;
import com.tdd.domain.Customer;
import com.tdd.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

/*
The @WebFluxTest slice lets you isolate the web tier machinery from everything else in the Spring application context
 */
@WebFluxTest
@Import(CustomerWebConfiguration.class)
@RunWith(SpringRunner.class)
public class CustomerWebTest {

/*
The Spring Test Framework defines the reactive WebTestClient, which is sort of the reactive analog
to the MockMvc mock client from the Servlet-centric Spring MVC world
 */
    @Autowired
    private WebTestClient client;

/*
@MockBean tells Spring to either replace any bean in the bean registry with a Mockito mock bean of
the same type as the annotated field or to add a Mockito mock bean to the bean registry if no such bean exists.
 */
    @MockBean
    private CustomerRepository repository;

    @Test
    public void getAll() {
        Mockito.when(this.repository.findAll())
                .thenReturn(Flux.just(new Customer("1", "A"), new Customer("2", "B")));

        this.client.get()
                .uri("/customers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk().expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo("1")
                .jsonPath("$.[0].name").isEqualTo("A")
                .jsonPath("$.[1].id").isEqualTo("2")
                .jsonPath("$.[1].name").isEqualTo("B")
                ;

    }

}
