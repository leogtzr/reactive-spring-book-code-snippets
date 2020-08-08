package com.endpoints.timer;

import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

/*
The TimingExchangeFilterFunction takes the current request, wraps it in a wrapper class
(rsb.client.timer.TimingClientResponseWrapper) and then lets all subsequent filters in the filter chain
have their crack at the current request.
 */
public class TimingExchangeFilterFunction implements ExchangeFilterFunction {

    @Override
    public Mono<ClientResponse> filter(final ClientRequest clientRequest, final ExchangeFunction next) {
        return next.exchange(clientRequest)
                .map(currentResponse -> new TimingClientResponseWrapper(currentResponse));
        // 1
    }

}

/*
1)
Take the current response, the result of letting all subsequent filters in the filter chain have a crack
at the current request, and wrap it using TimingClientResponseWrapper.
 */