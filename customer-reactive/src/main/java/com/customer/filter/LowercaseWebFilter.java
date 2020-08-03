package com.customer.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class LowercaseWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(final ServerWebExchange currentRequest, final WebFilterChain webFilterChain) {
        final var lowercaseUri = URI.create(currentRequest.getRequest().getURI().toString().toLowerCase());

        // Mutate the incoming request, forwarding it on with a lowercase URI
        final var outgoingExchange = currentRequest.mutate()
                .request(builder -> builder.uri(lowercaseUri)).build();

        // forward  the request onward in the filter chain
        return webFilterChain.filter(outgoingExchange);
    }

}
