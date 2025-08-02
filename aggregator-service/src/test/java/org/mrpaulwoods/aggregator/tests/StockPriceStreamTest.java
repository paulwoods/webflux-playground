package org.mrpaulwoods.aggregator.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.MediaType;
import org.mrpaulwoods.aggregator.tests.dto.PriceUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.test.StepVerifier;

public class StockPriceStreamTest extends AbstractIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(StockPriceStreamTest.class);

    @Test
    public void priceStream() {
        // given:
        var responseBody = this.resourceToString("stock-service/stock-price-stream-200.jsonl");

        mockServerClient
                .when(HttpRequest.request("/stock/price-stream"))
                .respond(HttpResponse.response(responseBody)
                        .withStatusCode(200)
                        .withContentType(MediaType.parse("application/x-ndjson"))
                );

        this.client.get()
                .uri("/stock/price-stream")
                .accept(org.springframework.http.MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(PriceUpdate.class)
                .getResponseBody()
                .doOnNext(price -> log.info("####### price {}", price))
                .as(StepVerifier::create)
                .assertNext(p -> Assertions.assertEquals(53, p.price()))
                .assertNext(p -> Assertions.assertEquals(54, p.price()))
                .assertNext(p -> Assertions.assertEquals(55, p.price()))
                .expectComplete()
                .verify();


    }

}
