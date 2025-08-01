package org.mrpaulwoods.playground.tests.sec05;

import org.junit.jupiter.api.Test;
import org.mrpaulwoods.playground.sec04.dto.CustomerDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(properties = "sec=sec05")
public class CustomerServiceTest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(org.mrpaulwoods.playground.tests.sec05.CustomerServiceTest.class);

    @Autowired
    private WebTestClient client;

    @Test
    public void unauthorizedNoToken() {
        this.client.get()
                .uri("/customers")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void unauthorizedInvalidToken() {
        this.client.get()
                .uri("/customers")
                .header("auth-token", "xxxxxx")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void standardCategoryGetSuccess() {
        this.client.get()
                .uri("/customers")
                .header("auth-token", "secret123")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void standardCategoryCreateForbidden() {

        var dto = new CustomerDto(null, "marshal", "marshal@gmail.com");

        this.client.post()
                .uri("/customers")
                .header("auth-token", "secret123")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    public void standardCategoryUpdateForbidden() {

        var dto = new CustomerDto(null, "marshal", "marshal@gmail.com");

        this.client.put()
                .uri("/customers/10")
                .header("auth-token", "secret123")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    public void standardCategoryDeleteForbidden() {

        this.client.delete()
                .uri("/customers/10")
                .header("auth-token", "secret123")
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    public void primeCategoryGetSuccess() {
        this.client.get()
                .uri("/customers")
                .header("auth-token", "secret456")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void primeCategoryCreateAndDeleteSuccess() {

        var dto = new CustomerDto(null, "marshal", "marshal@gmail.com");

        this.client.post()
                .uri("/customers")
                .header("auth-token", "secret456")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isOk();

        this.client.delete()
                .uri("/customers/11")
                .header("auth-token", "secret456")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void primeCategoryUpdateForbidden() {

        var dto = new CustomerDto(null, "marshal", "marshal@gmail.com");

        this.client.put()
                .uri("/customers/10")
                .header("auth-token", "secret456")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isOk();
    }

}
