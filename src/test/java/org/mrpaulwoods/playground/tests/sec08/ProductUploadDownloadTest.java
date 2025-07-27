package org.mrpaulwoods.playground.tests.sec08;

import org.junit.jupiter.api.Test;
import org.mrpaulwoods.playground.sec08.dto.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class ProductUploadDownloadTest {

    private static final Logger log = LoggerFactory.getLogger(ProductUploadDownloadTest.class);

    private final ProductClient productClient = new ProductClient();

    @Test
    public void upload1() {

        var flux = Flux.just(new ProductDto(null, "iphone", 1000))
                .delayElements(Duration.ofSeconds(10));

        productClient.uploadProducts(flux)
                .doOnNext(r -> log.info("received {}", r))
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    public void upload2() {

        var flux = Flux.range(1, 10)
                .map(i -> new ProductDto(null, "product-" + i, i))
                .delayElements(Duration.ofSeconds(2));

        productClient.uploadProducts(flux)
                .doOnNext(r -> log.info("received {}", r))
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

}
