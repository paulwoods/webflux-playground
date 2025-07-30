package org.mrpaulwoods.playground.tests.sec10;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mrpaulwoods.playground.tests.sec10.dto.Product;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.test.StepVerifier;

public class Lec01HttpConnectionPoolingTest extends AbstractWebClient {

    private final WebClient client = createWebClient(b -> {

        // change the connection pool to poolSize, and change the queue size to poolSize * 5

        var poolSize = 1_000;

        var provider = ConnectionProvider.builder("vins")
                .lifo()
                .maxConnections(poolSize)
                .pendingAcquireMaxCount(poolSize * 5) // waiting queue size
                .build();

        var httpClient = HttpClient.create(provider)
                .compress(true)
                .keepAlive(true);

        b.clientConnector(new ReactorClientHttpConnector(httpClient));


    });

    private Mono<Product> getProduct(int id) {
        return client.get()
                .uri("/product/{id}", id)
                .retrieve()
                .bodyToMono(Product.class);
    }

    @Test
    public void concurrentRequests() {
        // flat map has a default size of 256
        // webclient has a default size of 500

        var max = 1_000;
        Flux.range(1, max)
                .flatMap(this::getProduct, max)
                .collectList()
                .as(StepVerifier::create)
                .assertNext(list -> Assertions.assertEquals(max, list.size()))
                .expectComplete()
                .verify();
    }

}

// watch 'netstat -an| grep -w 127.0.0.1.7070'