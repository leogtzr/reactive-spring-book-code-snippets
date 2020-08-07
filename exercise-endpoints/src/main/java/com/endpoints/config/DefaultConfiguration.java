package com.endpoints.config;

import com.endpoints.client.DefaultClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Log4j2
public class DefaultConfiguration {

    @Bean
    public DefaultClient defaultClient(final WebClient.Builder builder, ClientProperties properties) {
        final var root = properties.getHttp().getRootUrl();
        return new DefaultClient(builder.baseUrl(root).build());// <1>
    }


}
