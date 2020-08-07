package com.endpoints.config;

import com.endpoints.client.AuthenticatedClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

@Log4j2
@Configuration
public class AuthenticatedConfiguration {

    @Bean
    public AuthenticatedClient authenticatedClient(final WebClient.Builder builder, final ClientProperties clientProperties) {
        final var httpProperties = clientProperties.getHttp();
        final var basicAuthProperties = clientProperties.getHttp().getBasic();

        final var filterFunction = ExchangeFilterFunctions.basicAuthentication(
                basicAuthProperties.getUsername(), basicAuthProperties.getPassword()
        );

        final WebClient client = builder
                .baseUrl(httpProperties.getRootUrl())
                .filters(filters -> filters.add(filterFunction))
                .build()
                ;

        return new AuthenticatedClient(client);

    }

}
