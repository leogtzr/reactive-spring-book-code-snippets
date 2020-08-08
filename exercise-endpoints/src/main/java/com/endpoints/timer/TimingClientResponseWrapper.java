package com.endpoints.timer;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.web.reactive.function.BodyExtractor;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.support.ClientResponseWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Log4j2
public class TimingClientResponseWrapper extends ClientResponseWrapper {

    public TimingClientResponseWrapper(final ClientResponse delegate) {
        super(delegate);
    }

    private void start() {
        log.info("start @ " + Instant.now().toString());
    }

    private void stop() {
        log.info("stop @ " + Instant.now().toString());
    }

    private <T> Mono<T> log(final Mono<T> c) {
        return c.doOnSubscribe(s -> start()).doFinally(s -> stop());
    }

    private <T> Flux<T> log(final Flux<T> c) {
        return c.doOnSubscribe(s -> start()).doFinally(s -> stop());
    }

    @Override
    public <T> T body(final BodyExtractor<T, ? super ClientHttpResponse> extractor) {
        final T body = super.body(extractor);
        if (body instanceof Flux) {
            return (T) log((Flux) body);
        }
        if (body instanceof Mono) {
            return (T) log((Mono) body);
        }
        return body;
    }

    @Override
    public <T> Mono<T> bodyToMono(Class<? extends T> elementClass) {
        return log(super.bodyToMono(elementClass));
    }

    @Override
    public <T> Mono<T> bodyToMono(final ParameterizedTypeReference<T> elementTypeRef) {
        return log(super.bodyToMono(elementTypeRef));
    }

    @Override
    public <T> Flux<T> bodyToFlux(final Class<? extends T> elementClass) {
        return log(super.bodyToFlux(elementClass));
    }

    @Override
    public <T> Flux<T> bodyToFlux(final ParameterizedTypeReference<T> elementTypeRef) {
        return log(super.bodyToFlux(elementTypeRef));
    }

}
