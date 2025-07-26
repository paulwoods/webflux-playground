package org.mrpaulwoods.playground.tests.sec07;

import org.junit.jupiter.api.Test;
import org.mrpaulwoods.playground.sec02.entity.Product;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class Lec07BasicAuthTest extends AbstractWebClient {

    private final WebClient client = createWebClient(b ->
            b.defaultHeaders(h ->
                    h.setBasicAuth("java", "secret"))
    );

    @Test
    public void basicAuth() {
        this.client.get()
                .uri("/lec07/product/{id}", 1)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}
