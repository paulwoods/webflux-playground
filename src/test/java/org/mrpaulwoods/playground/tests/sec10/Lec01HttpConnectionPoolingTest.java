package org.mrpaulwoods.playground.tests.sec10;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mrpaulwoods.playground.tests.sec10.dto.Product;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class Lec01HttpConnectionPoolingTest extends AbstractWebClient {

    private final WebClient client = createWebClient();

    private Mono<Product> getProduct(int id) {
        return client.get()
                .uri("/product/{id}", id)
                .retrieve()
                .bodyToMono(Product.class);
    }

    @Test
    public void concurrentRequests() throws InterruptedException {
        var max = 10;
        Flux.range(1, max)
                .flatMap(this::getProduct)
                .collectList()
                .as(StepVerifier::create)
                .assertNext(list -> Assertions.assertEquals(max, list.size()))
                .expectComplete()
                .verify();

        Thread.sleep(Duration.ofMinutes(1));
    }

}

//Netstat command to monitor the network connections
//netstat -an| grep -w 127.0.0.1.7070
//
//To watch
//watch 'netstat -an| grep -w 127.0.0.1.7070'