package org.mrpaulwoods.playground.tests.sec09;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mrpaulwoods.playground.sec09.dto.ProductDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@AutoConfigureWebTestClient
@SpringBootTest(properties = "sec=sec09")
public class ServerSentEventsTest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ServerSentEventsTest.class);

    @Autowired
    private WebTestClient client;

    @Test
    public void serverSentEvents() {

        client.get()
                .uri("/products/stream/80")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDto.class)
                .getResponseBody()
                .take(3)
                .doOnNext(dto -> log.info("received: {}", dto))
                .collectList()
                .as(StepVerifier::create)
                .assertNext(list -> {
                    Assertions.assertEquals(3, list.size());
                    Assertions.assertTrue(list.stream().allMatch(dto -> dto.getPrice() <= 80));
                })
                .expectComplete()
                .verify();
    }

}
