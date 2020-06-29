package com.playground;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.test.StepVerifier;

@SpringBootTest
class ReactiveSnippetsApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void emitterProcessor() {
		EmitterProcessor<String> processor = EmitterProcessor.create();
		// Produce some items ...
		produce(processor.sink());
		consume(processor);
	}

	private void produce(final FluxSink<String> sink) {
		sink.next("1");
		sink.next("2");
		sink.next("3");
		sink.complete();
	}

	private void consume(final Flux<String> publisher) {
		StepVerifier.create(publisher).expectNext("1").expectNext("2").expectNext("3")
				.verifyComplete();
	}

}
