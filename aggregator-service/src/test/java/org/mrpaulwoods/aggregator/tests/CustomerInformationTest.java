package org.mrpaulwoods.aggregator.tests;

import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.MediaType;
import org.slf4j.Logger;

import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class CustomerInformationTest extends AbstractIntegrationTest {

    private static final Logger log = getLogger(CustomerInformationTest.class);

    @Test
    public void customerInformation() {
        // mock customer service
        var responseBody = this.resourceToString("customer-service/customer-information-200.json");
        mockServerClient
                .when(HttpRequest.request("/customers/1"))
                .respond(HttpResponse.response(responseBody)
                        .withStatusCode(200)
                        .withContentType(MediaType.APPLICATION_JSON)
                );

        this.client.get()
                .uri("/customers/1")
                .exchange()
                .expectBody()
                .consumeWith(e -> log.info("####### {}", new String(Objects.requireNonNull(e.getResponseBody()))));
    }

}
