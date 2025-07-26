package org.mrpaulwoods.playground.tests.sec07;

import org.junit.jupiter.api.Test;
import org.mrpaulwoods.playground.sec02.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.UUID;

public class Lec09ExchangeFilterTest extends AbstractWebClient {

    public static final Logger log = LoggerFactory.getLogger(Lec09ExchangeFilterTest.class);

    private final WebClient client = createWebClient(b -> b.filter(tokenGenerator()).filter(requestLogger()));

    @Test
    public void exchangeFilter() {
        for (int i = 0; i < 5; i++) {

            this.client.get()
                    .uri("/lec09/product/{id}", 1)
                    .retrieve()
                    .bodyToMono(Product.class)
                    .doOnNext(print())
                    .then()
                    .as(StepVerifier::create)
                    .expectComplete()
                    .verify();
        }
    }

    private ExchangeFilterFunction tokenGenerator() {
        return (request, next) -> {
            var token = UUID.randomUUID().toString().replace("-", "");
            log.info("generated token: {}", token);
            var modifiedRequest = ClientRequest.from(request).headers(h -> h.setBearerAuth(token)).build();
            return next.exchange(modifiedRequest);
        };
    }

    private ExchangeFilterFunction requestLogger() {
        return (request, next) -> {
            log.info("request url - {}: {}", request.method(), request.url());
            return next.exchange(request);
        };
    }

}
